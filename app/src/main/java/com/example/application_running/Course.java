package com.example.application_running;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class Course extends AppCompatActivity {
    private TextView DistanceView;
    private double distance;
    private Chronometer chronometer1;
    private long pauseOffset;
    private boolean running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        DistanceView=findViewById(R.id.text1);
        //Recuperation de la valeur de la distance à parcourir
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("distance")){
                distance = intent.getDoubleExtra("distance", 0.0);
            }
        }
        initialisationDistance();
        chronometer1 = findViewById(R.id.chronometer1);
        chronometer1.setBase(SystemClock.elapsedRealtime());
        chronometer1.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(Course.this, "Bing!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initialisationDistance(){
        DistanceView.setText(String.valueOf(distance));
    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer1.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer1.start();
            running = true;
        }
    }
    public void pauseChronometer(View v) {
        if (running) {
            chronometer1.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer1.getBase();
            running = false;
        }
    }
    public void resetChronometer(View v) {
        chronometer1.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
}