package com.example.tareas2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.tareas2.db.ControladorDB;

public class Principal extends AppCompatActivity {
    private ControladorDB controladorDB;
    private ListView listViewTareas;
    private Util util=new Util(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        controladorDB=new ControladorDB(this);
        listViewTareas=findViewById(R.id.listatareas);
        actualizaListaTareas();
        ponPeces();
        Peces peces = new Peces(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_task: {
                addnuevaTarea();
                return true;
            }
            case R.id.cerrar_sesion:{
                cerrarSesion();
                return true;
            }
            case R.id.completadas:{
                irACompletadas();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion(){
        AlertDialog dialog=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.MiEstiloDialogo))
                .setTitle("Cerrar sesión")
                .setMessage("¿Esta seguro que desea cerrar sesión")
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        irALogin();
                    }
                })
                .setNegativeButton("Cancelar",null)
                .create();
        dialog.show();
    }
    private void irACompletadas(){
        Intent intent=new Intent(this,TerminadasActivity.class);
        intent.putExtra("idUser",getIdUser());
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }
    public void irALogin(){
        View v=getLayoutInflater().inflate(R.layout.tostada_layout,(ViewGroup) findViewById(R.id.layout_linear));
        util.tostada("Cerrando Sesión",v);
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }
    private void addnuevaTarea() {
        Intent intent=new Intent(this,NuevaTareaActivity.class);
        int idUser = getIdUser();
        System.out.println(idUser);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);

    }

    private int getIdUser() {
        return getIntent().getIntExtra("idUser",0);
    }

    private void actualizaListaTareas(){
        if(controladorDB.nRegistros(getIdUser(),0)==0)
            listViewTareas.setAdapter(null);
        else {
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, R.layout.item_tarea, R.id.nombre_tarea, controladorDB.obtenerTareas(getIdUser(),0));
            listViewTareas.setAdapter(adaptador);
        }
    }

    public void terminarTarea(final View view){
        final View v=getLayoutInflater().inflate(R.layout.tostada_layout,(ViewGroup) findViewById(R.id.layout_linear));
        AlertDialog dialog=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.MiEstiloDialogo))
                .setTitle("TerminarTarea")
                .setMessage("¿Estás seguro que quieres terminar la tarea?")
                .setPositiveButton("Terminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controladorDB.realizarTarea(1,util.sacaIdTatea(view),util.obtenerFecha()[0],util.obtenerFecha()[1]);
                        actualizaListaTareas();
                        util.tostada("Tarea terminada",v);
                    }
                })
                .setNegativeButton("Cancelar",null)
                .create();
        dialog.show();
        actualizaListaTareas();
    }

    public void editarTarea(View view){
        Intent intent=new Intent(this,EditarTareaActivity.class);
        intent.putExtra("idTarea",util.sacaIdTatea(view));
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }
    
    public void borrarTarea(final View view){
        final View v=getLayoutInflater().inflate(R.layout.tostada_layout,(ViewGroup) findViewById(R.id.layout_linear));
        AlertDialog dialog=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.MiEstiloDialogo))
                .setTitle("Eliminar Tarea")
                .setMessage("¿Estás seguro que deseas eliminar la tarea?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controladorDB.borrarTarea(util.sacaIdTatea(view));
                        actualizaListaTareas();
                        util.tostada("Tarea eliminada con éxito",v);
                    }
                })
                .setNegativeButton("CAncelar",null)
                .create();
        dialog.show();
        actualizaListaTareas();
    }
    private void ponPeces() {
        final FrameLayout fl=findViewById(R.id.fl);
        ImageView[] pez=new ImageView[8];
        for(int p=0;p<pez.length;p++) {
            pez[p] = new ImageView(this);
            if (p<pez.length/2)
                pez[p].setImageDrawable(getDrawable(R.drawable.ic_peces));
            else pez[p].setImageDrawable(getDrawable(R.drawable.ic_peces2));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            pez[p].setLayoutParams(params);
            fl.addView(pez[p], 1);
            if (p<pez.length/2)
                pez[p].setX(-300);
            else
                pez[p].setX(1200);
            pez[p].setY(300*p);
            int i = 500;
            pez[p].setId(i+p);
        }
    }
}



