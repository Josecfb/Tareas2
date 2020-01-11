package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.example.tareas2.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private ControladorDB controladorDB=new ControladorDB(this);
    private Util util=new Util(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        TextInputLayout l_contrasena=findViewById(R.id.l_contrasena);
        Typeface mifuente=Typeface.createFromAsset(getAssets(),"indieflowerregular.ttf");
        l_contrasena.setTypeface(mifuente);
        util.ponPeces(this);
        new Peces(this);
    }


    public void crearCuenta(View view){
        Intent intent=new Intent(this,RegistroActivity.class);
        startActivity(intent);
        finish();
    }

    public void login(View view){
        TextInputEditText user=findViewById(R.id.user);
        TextInputEditText password=findViewById(R.id.password);
        View v=getLayoutInflater().inflate(R.layout.tostada_layout,(ViewGroup) findViewById(R.id.layout_linear));
        if (user.getText().toString().equals("")) {
            util.tostada("el usuario no puede estár\nen blanco",v);
        }else if (password.getText().toString().equals("")){
            util.tostada("La contraseña no puede\nestár vacía",v);
        }else if(controladorDB.existe(user.getText().toString(),password.getText().toString().hashCode())==0) {
            util.tostada("Ususario o contraseña\nno válidos",v);
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
