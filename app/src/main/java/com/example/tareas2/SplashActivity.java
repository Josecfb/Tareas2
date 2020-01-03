package com.example.tareas2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        TextView titulo=findViewById(R.id.titulo);
        ImageView logo=findViewById(R.id.logo);
        Animation aLogo=AnimationUtils.loadAnimation(this,R.anim.logo);
        Animation entrada= AnimationUtils.loadAnimation(this,R.anim.entrada);
        logo.startAnimation(aLogo);
        titulo.startAnimation(entrada);
        aLogo.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent=new Intent(this,LoginActivity.class);

        startActivity(intent);
        overridePendingTransition(R.anim.desaparece, R.anim.aparece);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
