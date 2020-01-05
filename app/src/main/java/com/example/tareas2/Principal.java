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
import android.widget.ArrayAdapter;
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
        actualizarUI();
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
        }
        addnuevaTarea();
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
    private void irALogin(){
        util.tostada("Cerrando Sesión");
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

    private void actualizarUI(){
        if(controladorDB.nRegistros(getIdUser())==0)
            listViewTareas.setAdapter(null);
        else {
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, R.layout.item_tarea, R.id.nombre_tarea, controladorDB.obtenerTareas(getIdUser()));
            listViewTareas.setAdapter(adaptador);
        }
    }

    public void terminarTarea(final View view){
        AlertDialog dialog=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.MiEstiloDialogo))
                .setTitle("TerminarTarea")
                .setMessage("¿Estás seguro que quieres terminar la tarea?")
                .setPositiveButton("Terminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controladorDB.realizarTarea(sacaIdTatea(view));
                        actualizarUI();
                        util.tostada("Tarea terminada");
                    }
                })
                .setNegativeButton("Cancelar",null)
                .create();
        dialog.show();
        actualizarUI();
    }

    public void editarTarea(View view){
        Intent intent=new Intent(this,EditarTareaActivity.class);
        intent.putExtra("idTarea",sacaIdTatea(view));
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }

    private int sacaIdTatea(View view) {
        View parent=(View) view.getParent();
        final TextView tareaTextView=parent.findViewById(R.id.nombre_tarea);
        String nombreTarea=tareaTextView.getText().toString().split("\n")[0];
        String fecha=tareaTextView.getText().toString().split("\n")[1].split(" a las ")[0];
        String hora=tareaTextView.getText().toString().split("\n")[1].split(" a las ")[1];
        return controladorDB.idTarea(nombreTarea,fecha,hora);
    }
}



