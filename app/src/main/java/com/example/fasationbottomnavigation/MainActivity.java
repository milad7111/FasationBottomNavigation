package com.example.fasationbottomnavigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private MapboxMap map;
    private FasationBottomNavigation fasationBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);

        getSupportActionBar().hide();

        fasationBottomNavigation = findViewById(R.id.fasationBottomNavigation);
//        fasationBottomNavigation.getItemByIndex()
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        map.getUiSettings().setLogoEnabled(false);
        map.getUiSettings().setAttributionEnabled(false);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.732653, 51.422558), 15));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fasationBottomNavigation.onDestroy();
    }
}
