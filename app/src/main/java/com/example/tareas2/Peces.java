package com.example.tareas2;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Random;

public class Peces implements  Runnable{
    private Thread hilo;
    private Activity activity;
    private Random rnd;
    int id=500;
    final int PECES=8;

    public Peces(Activity activity){
        hilo=new Thread(this);
        hilo.start();
        this.activity=activity;


    }

    @Override
    public void run() {
        rnd=new Random(Calendar.getInstance().hashCode());
        ImageView[] pez=new ImageView[PECES];
        int[] mls=new int[pez.length/2];
        int[] y=new int[pez.length];
        Animation[] aPeces=new Animation[pez.length];
        for (int p=0;p<pez.length;p++){
            pez[p]=activity.findViewById(id+p);
            if (p<pez.length/2)
                aPeces[p] = AnimationUtils.loadAnimation(activity,R.anim.peces);
            else
                aPeces[p] = AnimationUtils.loadAnimation(activity,R.anim.peces2);

        }


        while (true){

            for (int p=0;p<pez.length/2;p++){
                mls[p]=rnd.nextInt(10000)+5000;
                pez[p].setScaleX(10000f/mls[p]);
                pez[p].setScaleY(10000f/mls[p]);
                pez[p+pez.length/2].setScaleX(10000f/mls[p]);
                pez[p+pez.length/2].setScaleY(10000f/mls[p]);
                aPeces[p].setStartOffset(p*800);
                aPeces[p+pez.length/2].setStartOffset(p*800);
                aPeces[p].setDuration(mls[p]);
                aPeces[p+pez.length/2].setDuration(mls[p]);
                y[p+pez.length/2]=rnd.nextInt(2000);
                pez[p+pez.length/2].setY(y[p+pez.length/2]);
                pez[p+pez.length/2].startAnimation((aPeces[p+pez.length/2]));
                y[p]=rnd.nextInt(2000);
                pez[p].setY(y[p]);
                pez[p].startAnimation((aPeces[p]));
            }

            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
