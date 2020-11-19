package com.example.application_running;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Maps_course_duo extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText etSource,etDestination;
    private TextView textView;
    private String sType;
    private double lat1=0,long1=0,lat2=0,long2=0;
    private int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_course_duo);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

        etSource = findViewById(R.id.et_source);
        etDestination=findViewById(R.id.et_destination);
        textView=findViewById(R.id.text_view);

        Places.initialize(getApplicationContext(),"AIzaSyD9NEJVDGrCl9s4vDwxgdAEHm2Ko717aJg");
        etSource.setFocusable(false);
        etSource.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                sType="source";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields).build(Maps_course_duo.this);
                startActivityForResult(intent,100);

            }
        });
        etDestination.setFocusable(false);
        etDestination.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
              sType="destination";
              List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG);
              Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields).build(Maps_course_duo.this);
              startActivityForResult(intent,100);
          }
        });

        textView.setText("0.0 Kilometers");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            if(sType.equals("source")){
                flag++;
                etSource.setText(place.getAddress());
                String sSource = String.valueOf(place.getAddress());
                sSource = sSource.replaceAll("lat/lng:","");
                sSource = sSource.replace("(","");
                sSource = sSource.replace(")","");
                String[] split = sSource.split(",");
                lat1=Double.parseDouble(split[0]);
                long1=Double.parseDouble(split[1]);
            }else{
                flag++;
                etDestination.setText(place.getAddress());
                String sSource = String.valueOf(place.getAddress());
                sSource = sSource.replaceAll("lat/lng;","");
                sSource = sSource.replace("(","");
                sSource = sSource.replace(")","");
                String[] split = sSource.split(",");
                lat2=Double.parseDouble(split[0]);
                long2=Double.parseDouble(split[1]);

            }

            if(flag>=2){
                distance(lat1,long1,lat2,long2);
            }
        }else if(requestCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void distance(double lat1,double long1,double lat2,double long2){
        double longDiff=long1-long2;
        double distance=Math.sin(deg2rad(lat1))*Math.sin(deg2rad(lat2))+Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(longDiff));
        distance=Math.acos(distance);
        distance=rad2deg(distance);
        distance=distance*60*1.1515;
        distance=distance*1.609344;
        textView.setText(String.format(Locale.US,"%2f Kilometers",distance));
    }

    private double rad2deg(double distance) {
        return (distance*180.0/Math.PI);
    }

    private double deg2rad(double lat1) {
        return (lat1*Math.PI/180.0);
    }
}