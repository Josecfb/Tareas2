package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tareas2.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

public class EditarTareaActivity extends AppCompatActivity {
    private ControladorDB controladorDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controladorDB=new ControladorDB(this);
        setContentView(R.layout.activity_editar_tarea);
        TextInputEditText nombreTarea=findViewById(R.id.task);
        TextInputEditText fecha=findViewById(R.id.fecha);
        TextInputEditText hora=findViewById(R.id.hora);
        System.out.println(controladorDB.devolverTarea(getIdTarea())[0]);
        nombreTarea.setText(controladorDB.devolverTarea(getIdTarea())[0]);
        fecha.setText(controladorDB.devolverTarea(getIdTarea())[1]);
        hora.setText(controladorDB.devolverTarea(getIdTarea())[2]);

    }
    private int getIdTarea(){
        return getIntent().getIntExtra("idTarea",0);
    }
    public void guardarLaTarea(View view){
        TextInputEditText nombreTarea=findViewById(R.id.task);
        TextInputEditText fecha=findViewById(R.id.fecha);
        TextInputEditText hora=findViewById(R.id.hora);
        controladorDB.guardarTarea(getIdTarea(),nombreTarea.getText().toString(),fecha.getText().toString(),hora.getText().toString());
        Intent intent=new Intent(this,Principal.class);
        intent.putExtra("idUser", Integer.parseInt(controladorDB.devolverTarea(getIdTarea())[3]));
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }
}
