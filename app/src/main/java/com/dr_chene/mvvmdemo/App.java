package com.dr_chene.mvvmdemo;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context;
    public static boolean connected = false;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
