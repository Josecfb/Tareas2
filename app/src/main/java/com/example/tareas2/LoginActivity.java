package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.tareas2.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private ControladorDB controladorDB=new ControladorDB(this);
    private Util util=new Util(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void crearCuenta(View view){
        Intent intent=new Intent(this,RegistroActivity.class);
        startActivity(intent);
        finish();
    }

    public void login(View view){
        TextInputEditText user=findViewById(R.id.user);
        TextInputEditText password=findViewById(R.id.password);
        if (user.getText().toString().equals("")) {
            util.tostada("el usuario no puede estár en blanco");
        }else if (password.getText().toString().equals("")){
            util.tostada("La contraseña no puede estár vacía");
        }else if(controladorDB.existe(user.getText().toString(),password.getText().toString())==0) {
            util.tostada("Ususario o contraseña no válidos");
        }else{
            int idUser = (int) controladorDB.idUser(user.getText().toString());
            Intent intent = new Intent(this, Principal.class);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
            overridePendingTransition(R.anim.desaparece, R.anim.aparece);
            finish();
        }
    }
}
