package com.example.tareas2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.time.LocalDateTime;

import com.example.tareas2.db.ControladorDB;

public class Principal extends AppCompatActivity {
    private ControladorDB controladorDB;
    private ListView listViewTareas;

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
                nuevaTarea();
                return true;
            }
            case R.id.cerrar_sesion:{

                cerrarSesion();
                return true;
            }
        }
        nuevaTarea();
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion(){
        AlertDialog dialog=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.AlertDialogCustom))
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
        Toast toast=Toast.makeText(this,"Cerrando Sesión",Toast.LENGTH_LONG);
        toast.show();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }
    private void nuevaTarea() {
        LocalDateTime fecha=null;
        int dia=0,mes=0,year=0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fecha = LocalDateTime.now();
            dia=fecha.getDayOfMonth();
            mes=fecha.getMonthValue();
            year=fecha.getYear();
        }
        String tFecha=dia+"/"+mes+"/"+year;
        System.out.println(tFecha);
        final EditText cajaTareaNueva=new EditText(this);
        cajaTareaNueva.setTextColor(Color.WHITE);
        AlertDialog dialog=new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom))
                .setTitle("Nueva Tarea")
                .setMessage("Escribe tu nueva tarea")
                .setView(cajaTareaNueva)
                .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(cajaTareaNueva.getText().toString().equals("")){
                            tareaEnBlanco();
                        }else {
                            controladorDB.addTask(cajaTareaNueva.getText().toString(), getIdUser());
                            actualizarUI();
                        }
                    }
                })
                .setNegativeButton("Cancelar",null)
                .create();
        dialog.show();
    }
    private void tareaEnBlanco() {
        Toast toast=Toast.makeText(this,"La tarea no puede estar en blanco",Toast.LENGTH_SHORT);
        toast.show();
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

    public void borrarTarea(View view){
        View parent=(View) view.getParent();
        final TextView tareaTextView=parent.findViewById(R.id.nombre_tarea);

        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle("TerminarTarea")
                .setMessage("¿Estás seguro que quieres terminar la tarea?")
                .setPositiveButton("Terminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nombreTarea=tareaTextView.getText().toString();
                        controladorDB.realizarTarea(nombreTarea);
                        actualizarUI();
                        tareaTerminada();
                    }
                })
                .setNegativeButton("Cancelar",null)
                .create();
        dialog.show();
        actualizarUI();
    }
    private void tareaTerminada() {
        Toast toast=Toast.makeText(this,"Tarea terminada",Toast.LENGTH_SHORT);
        toast.show();
    }
    public void editarTarea(View view){
        View parent=(View) view.getParent();
        final TextView tareaTextView=parent.findViewById(R.id.nombre_tarea);
        final EditText cajaTexto=new EditText(this);
        cajaTexto.setText(tareaTextView.getText().toString());

        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle("Editar tarea")
                .setMessage("Modifica la tarea")
                .setView(cajaTexto)
                .setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controladorDB.editarTarea(cajaTexto.getText().toString(),tareaTextView.getText().toString());
                        actualizarUI();
                    }
                })
                .setNegativeButton("Cancelar",null)
                .create();
        dialog.show();
        actualizarUI();
    }
}



