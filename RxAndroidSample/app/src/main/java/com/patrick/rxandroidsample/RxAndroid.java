package com.patrick.rxandroidsample;

import android.app.Application;

import com.patrick.rxandroidsample.volley.LocalVolley;

public class RxAndroid extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LocalVolley.init(this);
    }
}
