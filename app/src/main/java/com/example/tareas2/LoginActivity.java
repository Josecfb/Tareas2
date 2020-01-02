package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tareas2.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private ControladorDB controladorDB=new ControladorDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void crearCuenta(View view){
        Intent intent=new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }

    public void login(View view){
        TextInputEditText user=findViewById(R.id.user);
        TextInputEditText password=findViewById(R.id.password);
        System.out.println("hola");
        if(controladorDB.existe(user.getText().toString(),password.getText().toString())==0) {
            Toast toast = Toast.makeText(this, "Ususario o contraseña no válidos", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            int idUser = (int) controladorDB.idUser(user.getText().toString());
            Intent intent = new Intent(this, Principal.class);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
        }
    }
}
