package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tareas2.db.ControladorDB;
import com.google.android.material.textfield.TextInputLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegistroActivity extends AppCompatActivity {
    private ControladorDB controladorDB=new ControladorDB(this);
    private Util util=new Util(this);
    private Peces peces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Typeface mifuente=Typeface.createFromAsset(getAssets(),"indieflowerregular.ttf");
        TextInputLayout lcontrasena=findViewById(R.id.l_ncontrasena);
        TextInputLayout lcontrasena2=findViewById(R.id.l_ncontrasena2);
        lcontrasena.setTypeface(mifuente);
        lcontrasena2.setTypeface(mifuente);
        ponPeces();
        peces=new Peces(this);

    }

    public void registrar(View view){
        TextView user=findViewById(R.id.user);
        TextView password1=findViewById(R.id.password);
        TextView password2=findViewById(R.id.password2);
        View v=getLayoutInflater().inflate(R.layout.tostada_layout,(ViewGroup) findViewById(R.id.layout_linear));
        if (user.getText().toString().equals(""))
            util.tostada("El nombre de usuario\nno puede estár en blanco",v);
        else
            if (password1.getText().toString().equals(""))
                util.tostada("El password\nno puede estár vacío",v);
            else
                if (!password1.getText().toString().equals(password2.getText().toString()))
                    util.tostada("Los dos passwords\nno coinciden",v);
                else
                    if (controladorDB.existe(user.getText().toString(),password1.getText().toString())==1)
                        util.tostada("Este usuario\nya existe",v);
                    else{
                        controladorDB.addUser(user.getText().toString(), password1.getText().toString());
                        int idUser=controladorDB.idUser(user.getText().toString());
                        Intent intent = new Intent(this, Principal.class);
                        intent.putExtra("idUser", idUser);
                        startActivity(intent);
                        finish();
                    }
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
