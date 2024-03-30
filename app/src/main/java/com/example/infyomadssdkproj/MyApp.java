package com.example.infyomadssdkproj;

import android.app.Application;

import com.infyom.adssdk.InfyOmAds;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InfyOmAds.initializeAds(this);
    }
}
