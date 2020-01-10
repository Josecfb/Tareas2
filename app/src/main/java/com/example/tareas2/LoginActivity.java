package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
        ponPeces();


        l_contrasena.setTypeface(mifuente);
        Peces peces = new Peces(this);


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
        }else if(controladorDB.existe(user.getText().toString(),password.getText().toString())==0) {
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
