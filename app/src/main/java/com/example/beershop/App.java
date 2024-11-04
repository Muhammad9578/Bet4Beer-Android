package com.example.beershop;

import android.app.Application;

import com.squareup.otto.Bus;

public class App extends Application {

    public static Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new Bus();
    }
}
