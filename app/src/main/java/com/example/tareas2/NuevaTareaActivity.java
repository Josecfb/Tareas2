package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tareas2.db.ControladorDB;

public class NuevaTareaActivity extends AppCompatActivity {
    private ControladorDB controladorDB=new ControladorDB(this);
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util =new Util(this);
        setContentView(R.layout.activity_nueva_tarea);
        TextView tVFecha=findViewById(R.id.fecha);
        TextView tVHora=findViewById(R.id.hora);
        tVFecha.setText(util.obtenerFecha()[0]);
        tVHora.setText(util.obtenerFecha()[1]);
        util.ponPeces(this);
        new Peces(this);
    }

    public void nuevaTarea(View view){
        TextView cajaTareaNueva=findViewById(R.id.task);
        TextView tVfecha=findViewById(R.id.fecha);
        TextView tVHora=findViewById(R.id.hora);
        View v=getLayoutInflater().inflate(R.layout.tostada_layout,(ViewGroup) findViewById(R.id.layout_linear));
        if(cajaTareaNueva.getText().toString().equals("")){
            util.tostada("La tarea no puede\nestar en blanco",v);
        }else {
            System.out.println(getIntent().getIntExtra("idUser",0));
            controladorDB.addTask(cajaTareaNueva.getText().toString(),tVfecha.getText().toString(),tVHora.getText().toString(), getIntent().getIntExtra("idUser",0));
            cerrarNuevaTarea(view);
        }
    }
    public void cerrarNuevaTarea(View view){
        Intent intent=new Intent(this,Principal.class);
        intent.putExtra("idUser",getIntent().getIntExtra("idUser",0));
        startActivity(intent);
    }

    public void cuadroFecha(View view){
        util.cuadroFecha(view);
    }
    public void cuadroHora(View view) {
        util.cuadroHora(view);
    }
}
