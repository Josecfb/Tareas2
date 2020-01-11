package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.tareas2.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditarCompletadaActivity extends AppCompatActivity {
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
        TextInputLayout lfecha=findViewById(R.id.l_fecha);
        TextInputLayout lhora=findViewById(R.id.l_hora);
        CharSequence fc="Fecha de fin";
        lfecha.setHint(fc);
        lhora.setHint("Hora de fin");
        nombreTarea.setText(controladorDB.devolverTareaCompletada(getIdTarea())[0]);
        fecha.setText(controladorDB.devolverTareaCompletada(getIdTarea())[1]);
        hora.setText(controladorDB.devolverTareaCompletada(getIdTarea())[2]);
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
        controladorDB.guardarTareaTerminada(getIdTarea(),nombreTarea.getText().toString(),fecha.getText().toString(),hora.getText().toString());
        cerrarEditarTarea(view);
    }

    public void cerrarEditarTarea(View view){
        Intent intent=new Intent(this, TerminadasActivity.class);
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
