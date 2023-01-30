package com.example.test0130_cc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    MapFragment mapFragment;
    GroundOverlayOptions groundOverlayOptions;

    ArrayList<GroundOverlayOptions> arrayList = new ArrayList<GroundOverlayOptions>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("연습문제 13-7");

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); //초기 위성지도
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.879923,128.628126),15));   //지도중심이동
        googleMap.getUiSettings().setZoomControlsEnabled(true);//확대축소버튼

        //지도클릭하면 동작
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                groundOverlayOptions = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.cctv2)).position(latLng,100f,100f);
                arrayList.add(groundOverlayOptions);
                googleMap.clear();
                for(int i = 0; i < arrayList.size(); i++){
                    googleMap.addGroundOverlay(arrayList.get(i));
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0,1,0,"위성지도");
        menu.add(0,2,0,"일반지도");
        menu.add(0,3,0,"바로전 cctv지우기");
        menu.add(0,4,0,"모든 cctv지우기");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case 2:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case 3:
                googleMap.clear();
                arrayList.remove(arrayList.size()-1);
                for(int i = 0; i < arrayList.size(); i++){
                    googleMap.addGroundOverlay(arrayList.get(i));
                }
                return true;
            case 4:
                googleMap.clear();
                arrayList.clear();

        }

        return false;
    }
}