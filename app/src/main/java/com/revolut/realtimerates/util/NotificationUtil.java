package com.revolut.realtimerates.util;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

public class NotificationUtil {
    public static void showSnackBarNotification(@NotNull View view, String text) {
        showSnackBarNotification(view, text, null);
    }

    public static void showSnackBarNotification(@NotNull View view, String text, View.OnClickListener clickListener) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        if (clickListener != null) {
            snackbar.setAction("", clickListener);
        }
        snackbar.show();
    }
}
