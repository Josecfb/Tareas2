package com.example.tareas2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tareas2.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

import static com.google.android.material.resources.MaterialResources.getDrawable;

public class Util extends AppCompatActivity {
    private Context context;
    private ControladorDB controladorDB;

    public Util(@Nullable Context context) {
        this.context=context;
        controladorDB=new ControladorDB(context);
    }

    public String[] obtenerFecha(){
        String[] res=new String[2];
        Calendar fecha;
        int dia,mes,year,hora,minuto;
        fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH)+1;
        year = fecha.get(Calendar.YEAR);
        hora=fecha.get(Calendar.HOUR_OF_DAY);
        minuto=fecha.get(Calendar.MINUTE);
        res[0]=(dia<10?"0"+dia:dia)+"/"+(mes<10?"0"+mes:mes)+"/"+year;
        res[1]=(hora<10?"0"+hora:hora)+":"+(minuto<10?"0"+minuto:minuto);
        return res;
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
        TimePickerDialog horario=new TimePickerDialog(new ContextThemeWrapper(context,R.style.MiEstiloDialogoHora), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int minuto) {
                tHora.setText((hora<10?"0"+hora:hora)+":"+(minuto<10?"0"+minuto:minuto));
            }
        },hora,minuto,true);
        horario.show();
    }

    public void tostada(String texto,View layout){
        Toast toast= new Toast(context);
        toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
       // View layout = inflater.inflate(R.layout.tostada_layout,(ViewGroup) findViewById(R.id.layout_linear));
        TextView mensaje=layout.findViewById(R.id.texto_tostada);
        mensaje.setText(texto);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public int sacaIdTatea(View view) {
        View parent=(View) view.getParent();
        final TextView tareaTextView=parent.findViewById(R.id.nombre_tarea);
        String nombreTarea=tareaTextView.getText().toString().split("\n")[0];
        String fecha=tareaTextView.getText().toString().split("\n")[1].split(" a las ")[0];
        String hora=tareaTextView.getText().toString().split("\n")[1].split(" a las ")[1];
        return controladorDB.idTarea(nombreTarea,fecha,hora);
    }


}
