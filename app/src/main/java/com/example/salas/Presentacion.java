package com.example.salas;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class Presentacion extends AppCompatActivity {
    private static final int TIME_OUT = 3000;
    private final ArrayList<Drawable> imagenes = new ArrayList<Drawable>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);

        imagenes.add(getResources().getDrawable(R.drawable.salon));


        ImageView imagen = findViewById(R.id.imgPresentacion);
        imagen.setImageDrawable(imagenes.get(0));
        imagen.setScaleType(ImageView.ScaleType.FIT_XY);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Presentacion.this, Login.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

    }

    private static int randomThis(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}