package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.tareas2.db.ControladorDB;
import java.util.Calendar;

public class NuevaTareaActivity extends AppCompatActivity {
    private ControladorDB controladorDB=new ControladorDB(this);
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util =new Util(this);
        setContentView(R.layout.activity_nueva_tarea);
        Calendar fecha;
        int dia,mes,year,hora,minuto;
        fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH)+1;
        year = fecha.get(Calendar.YEAR);
        hora=fecha.get(Calendar.HOUR_OF_DAY);
        minuto=fecha.get(Calendar.MINUTE);

        String tFecha=(dia<10?"0"+dia:dia)+"/"+(mes<10?"0"+mes:mes)+"/"+year;
        String tHora=(hora<10?"0"+hora:hora)+":"+(minuto<10?"0"+minuto:minuto);
        TextView tVFecha=findViewById(R.id.fecha);
        TextView tVHora=findViewById(R.id.hora);
        tVFecha.setText(util.obtenerFecha()[0]);
        tVHora.setText(util.obtenerFecha()[1]);
    }

    public void nuevaTarea(View view){
        TextView cajaTareaNueva=findViewById(R.id.task);
        TextView tVfecha=findViewById(R.id.fecha);
        TextView tVHora=findViewById(R.id.hora);
        if(cajaTareaNueva.getText().toString().equals("")){
            util.tostada("La tarea no puede estar en blanco");
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
