package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tareas2.db.ControladorDB;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegistroActivity extends AppCompatActivity {
    private ControladorDB controladorDB=new ControladorDB(this);
    private Util util=new Util(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void registrar(View view){
        TextView user=findViewById(R.id.user);
        TextView password1=findViewById(R.id.password);
        TextView password2=findViewById(R.id.password2);
        if (user.getText().toString().equals(""))
            util.tostada("El nombre de usuario no puede estár en blanco");
        else
            if (password1.getText().toString().equals(""))
                util.tostada("El password no puede estár vacío");
            else
                if (!password1.getText().toString().equals(password2.getText().toString()))
                    util.tostada("Escriba los dos passwords iguales");
                else
                    if (controladorDB.existe(user.getText().toString(),password1.getText().toString())==1)
                        util.tostada("Usuario ya registrado");
                    else{
                        controladorDB.addUser(user.getText().toString(), password1.getText().toString());
                        int idUser=controladorDB.idUser(user.getText().toString());
                        Intent intent = new Intent(this, Principal.class);
                        intent.putExtra("idUser", idUser);
                        startActivity(intent);
                        finish();
                    }
    }
}
