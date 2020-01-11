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
import android.widget.ListView;
import android.widget.TextView;
import com.example.tareas2.db.ControladorDB;

public class TerminadasActivity extends AppCompatActivity {
    private ControladorDB controladorDB=new ControladorDB(this);
    private ListView listViewTareas;
    private Util util=new Util(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        TextView titulo=findViewById(R.id.titulo_principal);
        titulo.setText("Tareas Completadas");
        listViewTareas=findViewById(R.id.listatareas);
        actualizaListaTareas();
        util.ponPeces(this);
        new Peces(this);
        util.ponPeces(this);
        new Peces(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_completadas,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.iniciadas: {
                irAIniciadas();
                return true;
            }
            case R.id.cerrar_sesion:{
                cerrarSesion();
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
    public void irALogin(){
        View v=getLayoutInflater().inflate(R.layout.tostada_layout,(ViewGroup) findViewById(R.id.layout_linear));
        util.tostada("Cerrando Sesión",v);
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }
    private void irAIniciadas(){
        Intent intent=new Intent(this,Principal.class);
        intent.putExtra("idUser",getIdUser());
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }
    public void deshacerTarea(final View view){
        final View v=getLayoutInflater().inflate(R.layout.tostada_layout,(ViewGroup) findViewById(R.id.layout_linear));
        AlertDialog dialog=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.MiEstiloDialogo))
                .setTitle("Deshacer Tarea")
                .setMessage("¿Estás seguro que quieres deshacer la tarea?")
                .setPositiveButton("Deshacer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controladorDB.realizarTarea(0,util.sacaIdTatea(view),"","");
                        actualizaListaTareas();
                        util.tostada("Tarea deshecha",v);
                    }
                })
                .setNegativeButton("Cancelar",null)
                .create();
        dialog.show();
        actualizaListaTareas();
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
    public void editarTareaCompletada(View view){
        Intent intent=new Intent(this,EditarCompletadaActivity.class);
        intent.putExtra("idTarea",util.sacaIdTatea(view));
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }

    private void actualizaListaTareas(){
        if(controladorDB.nRegistros(getIdUser(),1)==0)
            listViewTareas.setAdapter(null);
        else {
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, R.layout.item_tarea_terminada, R.id.nombre_tarea, controladorDB.obtenerTareas(getIdUser(),1));
            listViewTareas.setAdapter(adaptador);
        }
    }
    private int getIdUser() {
        return getIntent().getIntExtra("idUser",0);
    }

}
