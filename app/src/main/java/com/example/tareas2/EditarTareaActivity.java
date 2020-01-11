package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.tareas2.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

public class EditarTareaActivity extends AppCompatActivity {
    private ControladorDB controladorDB;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controladorDB=new ControladorDB(this);
        util =new Util(this);
        setContentView(R.layout.activity_editar_tarea);
        TextInputEditText nombreTarea=findViewById(R.id.task);
        TextInputEditText fecha=findViewById(R.id.fecha);
        TextInputEditText hora=findViewById(R.id.hora);
        nombreTarea.setText(controladorDB.devolverTarea(getIdTarea())[0]);
        fecha.setText(controladorDB.devolverTarea(getIdTarea())[1]);
        hora.setText(controladorDB.devolverTarea(getIdTarea())[2]);
        util.ponPeces(this);
        new Peces(this);
    }
    private int getIdTarea(){
        return getIntent().getIntExtra("idTarea",0);
    }
    public void guardarLaTarea(View view){
        TextInputEditText nombreTarea=findViewById(R.id.task);
        TextInputEditText fecha=findViewById(R.id.fecha);
        TextInputEditText hora=findViewById(R.id.hora);
        controladorDB.guardarTarea(getIdTarea(),nombreTarea.getText().toString(),fecha.getText().toString(),hora.getText().toString());
        cerrarEditarTarea(view);
    }
    public void cerrarEditarTarea(View view){
        Intent intent=new Intent(this,Principal.class);
        intent.putExtra("idUser", Integer.parseInt(controladorDB.devolverTarea(getIdTarea())[3]));
        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }
    public void ponFecha(View view){
        util.cuadroFecha(view);
    }
    public void ponHora(View view){
        util.cuadroHora(view);
    }

}
