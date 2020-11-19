package com.example.application_running;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;



import android.os.Bundle;
import android.widget.Chronometer;
import android.os.SystemClock;
import android.widget.Toast;
import android.view.*;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Course_duo extends AppCompatActivity  {

    private Chronometer chronometer1;
    private Chronometer chronometer2;
    private long pauseOffset;
    private boolean running;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_duo);

        chronometer1 = findViewById(R.id.chronometer1);
        chronometer1.setBase(SystemClock.elapsedRealtime());

        chronometer2 = findViewById(R.id.chronometer2);
        chronometer2.setBase(SystemClock.elapsedRealtime());


        chronometer1.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(Course_duo.this, "Bing!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chronometer2.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(Course_duo.this, "Bing!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public void startChronometer(View v) {
        if (!running) {
            chronometer1.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer1.start();
            chronometer2.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer2.start();
            running = true;


        }
    }
    public void pauseChronometer(View v) {
        if (running) {
            chronometer1.stop();
            chronometer2.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer1.getBase();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer2.getBase();
            running = false;
        }
    }
    public void resetChronometer(View v) {
        chronometer1.setBase(SystemClock.elapsedRealtime());
        chronometer2.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

}