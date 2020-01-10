package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        ponPeces();
        Peces peces = new Peces(this);
    }

    public void nuevaTarea(View view){
        TextView cajaTareaNueva=findViewById(R.id.task);
        TextView tVfecha=findViewById(R.id.fecha);
        TextView tVHora=findViewById(R.id.hora);
        View v=getLayoutInflater().inflate(R.layout.tostada_layout,(ViewGroup) findViewById(R.id.layout_linear));
        if(cajaTareaNueva.getText().toString().equals("")){
            util.tostada("La tarea\nno puede estar en blanco",v);
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
    private void ponPeces() {
        final FrameLayout fl=findViewById(R.id.fl);
        ImageView[] pez=new ImageView[8];
        for(int p=0;p<pez.length;p++) {
            pez[p] = new ImageView(this);
            if (p<pez.length/2)
                pez[p].setImageDrawable(getDrawable(R.drawable.ic_peces));
            else pez[p].setImageDrawable(getDrawable(R.drawable.ic_peces2));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            pez[p].setLayoutParams(params);
            fl.addView(pez[p], 1);
            if (p<pez.length/2)
                pez[p].setX(-300);
            else
                pez[p].setX(1200);
            pez[p].setY(300*p);
            int i = 500;
            pez[p].setId(i+p);
        }
    }
}
