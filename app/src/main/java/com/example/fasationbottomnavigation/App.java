package com.example.fasationbottomnavigation;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Mapbox.getInstance(this,"pk.eyJ1IjoiYWxpa2hvc2hyYWZ0YXIiLCJhIjoiY2o1eDBxcjZwMGV3ejM0cWw5dG12bmpkMiJ9.DdlQAVKqK5T79uUknkL4lg");
    }
}
