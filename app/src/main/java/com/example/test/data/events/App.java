package com.example.test.data.events;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MapKitFactory.setApiKey("ef102e88-0fd5-4a84-9e22-6e50e100302d");
        MapKitFactory.initialize(this);
    }
}