package com.revolut.realtimerates;

import android.app.Application;

import com.revolut.realtimerates.domain.room.DatabaseManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class RateApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        DatabaseManager.getInstance(this);
    }
}
