package com.raj.zoho;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public class AppApplication extends Application {
    public static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Stetho.initializeWithDefaults(this);
    }
}
