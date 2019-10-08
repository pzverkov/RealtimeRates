package com.revolut.realtimerates;

import android.app.Application;

import com.revolut.realtimerates.domain.room.DatabaseManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class RateApp extends Application {
    private static RateApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fabric.with(instance, new Crashlytics());
        DatabaseManager.getInstance();
    }

    public static RateApp getInstance() {
        return instance;
    }
}
