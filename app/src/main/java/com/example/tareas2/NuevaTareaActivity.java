package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.tareas2.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.util.Calendar;

public class NuevaTareaActivity extends AppCompatActivity {
    private ControladorDB controladorDB=new ControladorDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);
        Calendar fecha=null;
        int dia=0,mes=0,year=0,hora=0,minuto=0;
        fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH)+1;
        year = fecha.get(Calendar.YEAR);
        hora=fecha.get(Calendar.HOUR_OF_DAY);

        String tFecha=(dia<10?"0"+dia:dia)+"/"+(mes<10?"0"+mes:mes)+"/"+year;
        String tHora=(hora<10?"0"+hora:hora)+":"+(minuto<10?"0"+minuto:minuto);
        TextView tVFecha=findViewById(R.id.fecha);
        TextView tVHora=findViewById(R.id.hora);
        tVFecha.setText(tFecha);
        tVHora.setText(tHora);
    }

    public void nuevaTarea(View view){
        TextView cajaTareaNueva=findViewById(R.id.task);
        TextView tVfecha=findViewById(R.id.fecha);
        TextView tVHora=findViewById(R.id.hora);
        if(cajaTareaNueva.getText().toString().equals("")){
            tareaEnBlanco();
        }else {
            System.out.println(getIntent().getIntExtra("idUser",0));
            controladorDB.addTask(cajaTareaNueva.getText().toString(),tVfecha.getText().toString(),tVHora.getText().toString(), getIntent().getIntExtra("idUser",0));

            Intent intent=new Intent(this,Principal.class);
            intent.putExtra("idUser",getIntent().getIntExtra("idUser",0));
            startActivity(intent);
        }
    }
    private void tareaEnBlanco() {
        Toast toast=Toast.makeText(this,"La tarea no puede estar en blanco",Toast.LENGTH_SHORT);
        toast.show();
    }
    public void cuadroFecha(View view){
        final TextInputEditText tFecha=findViewById(R.id.fecha);
        int year=Integer.parseInt(tFecha.getText().toString().split("/")[2]);
        int month=Integer.parseInt(tFecha.getText().toString().split("/")[1])-1;
        int day=Integer.parseInt(tFecha.getText().toString().split("/")[0]);
        DatePickerDialog calendario=new DatePickerDialog(new ContextThemeWrapper(this, R.style.MiEstiloDialogo),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                tFecha.setText((day<10?"0"+day:day)+"/"+(month+1<10?"0"+(month+1):(month+1))+"/"+year);
            }
        }, year, month, day);

        calendario.show();


    }
    public void cuadroHora(View view){
        final TextInputEditText tHora=findViewById(R.id.hora);
        int hora=Integer.parseInt(tHora.getText().toString().split(":")[0]);
        int minuto=Integer.parseInt(tHora.getText().toString().split(":")[1]);
        TimePickerDialog horario=new TimePickerDialog(new ContextThemeWrapper(this,R.style.AppTheme), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int minuto) {
                tHora.setText((hora<10?"0"+hora:hora)+":"+(minuto<10?"0"+minuto:minuto));
            }
        },hora,minuto,true);
        horario.show();

    }
}
