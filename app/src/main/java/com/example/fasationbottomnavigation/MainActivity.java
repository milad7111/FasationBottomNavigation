package com.example.fasationbottomnavigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity
        implements
        FasationBottomNavigationItemsClickListener,
        OnMapReadyCallback {

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
        fasationBottomNavigation.onItemClickListener(this);
        fasationBottomNavigation.setItemSolidStatus(4, true);
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        map.getUiSettings().setLogoEnabled(false);
        map.getUiSettings().setAttributionEnabled(false);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.732653, 51.422558), 15));
    }

    @Override
    public void onFasationBottomNavigationItemClick(int index) {
        switch (index) {
            case 0:
                Toast.makeText(this, "first", Toast.LENGTH_SHORT).show();
                fasationBottomNavigation.enableBadgeOnItem(3);
                break;
            case 1:
                Toast.makeText(this, "second", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "third", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                fasationBottomNavigation.disableBadgeOnItem(3);
                Toast.makeText(this, "fourth", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "fifth", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fasationBottomNavigation.onDestroy();
    }
}
