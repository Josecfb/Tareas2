package com.example.tareas2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

public class Util {
    private Context context;

    public Util(@Nullable Context context) {
        this.context=context;
    }

    public void cuadroFecha(View view){
        final TextInputEditText tFecha=view.findViewById(R.id.fecha);
        int year=Integer.parseInt(tFecha.getText().toString().split("/")[2]);
        int month=Integer.parseInt(tFecha.getText().toString().split("/")[1])-1;
        int day=Integer.parseInt(tFecha.getText().toString().split("/")[0]);

        DatePickerDialog calendario=new DatePickerDialog(new ContextThemeWrapper(context, R.style.MiEstiloDialogo),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                tFecha.setText((day<10?"0"+day:day)+"/"+(month+1<10?"0"+(month+1):(month+1))+"/"+year);
            }
        }, year, month, day);
        calendario.show();
    }

    public void cuadroHora(View view){
        final TextInputEditText tHora=view.findViewById(R.id.hora);
        int hora=Integer.parseInt(tHora.getText().toString().split(":")[0]);
        int minuto=Integer.parseInt(tHora.getText().toString().split(":")[1]);
        TimePickerDialog horario=new TimePickerDialog(new ContextThemeWrapper(context,R.style.AppTheme), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int minuto) {
                tHora.setText((hora<10?"0"+hora:hora)+":"+(minuto<10?"0"+minuto:minuto));
            }
        },hora,minuto,true);
        horario.show();
    }

    public void tostada(String texto){
        Toast toast=Toast.makeText(context,texto,Toast.LENGTH_LONG);
        toast.show();
    }
}
