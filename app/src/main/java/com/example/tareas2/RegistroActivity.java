package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tareas2.db.ControladorDB;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {
    private ControladorDB controladorDB=new ControladorDB(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }



    public void registrar(View view){
        TextView user=findViewById(R.id.user);
        TextView password1=findViewById(R.id.password);
        TextView password2=findViewById(R.id.password2);
        if (user.getText().toString().equals("")){
            Toast toast=Toast.makeText(this, "El nombre de usuario no puede estár en blanco",Toast.LENGTH_SHORT);
            toast.show();
        }else{
            if (password1.getText().toString().equals("")){
                Toast toast=Toast.makeText(this,"El password no puede estár vacío",Toast.LENGTH_SHORT);
                toast.show();
            }else{
                if (!password1.getText().toString().equals(password2.getText().toString())){
                    Toast toast=Toast.makeText(this,"Escriba los dos passwords iguales",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    if (controladorDB.existe(user.getText().toString(),password1.getText().toString())==1){
                        Toast toast=Toast.makeText(this,"Usuario ya registrado",Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        controladorDB.addUser(user.getText().toString(), password1.getText().toString());
                        int idUser = (int) controladorDB.idUser(user.getText().toString());
                        Intent intent = new Intent(this, Principal.class);
                        intent.putExtra("idUser", idUser);
                        startActivity(intent);
                    }
                }
            }
        }

    }
}
