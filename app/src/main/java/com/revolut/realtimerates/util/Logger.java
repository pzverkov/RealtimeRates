package com.revolut.realtimerates.util;

import android.util.Log;

import com.revolut.realtimerates.BuildConfig;

public class Logger {
    public static void i(String tag, String text){
        if (BuildConfig.DEBUG) {
            Log.i(tag, text);
        }
    }

    public static void e(String tag, String text){
        if (BuildConfig.DEBUG) {
            Log.e(tag, text);
        }
    }

    public static void w(String tag, String text){
        if (BuildConfig.DEBUG) {
            Log.w(tag, text);
        }
    }
}
