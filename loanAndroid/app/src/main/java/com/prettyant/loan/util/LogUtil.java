package com.prettyant.loan.util;

import android.util.Config;
import android.util.Log;

import com.prettyant.loan.BuildConfig;

public class LogUtil {

    public static final String LOG = "prettyant";

    public static void e(String message) {
        if (!BuildConfig.DEBUG) return;
        Log.e(LOG, message);
    }

    public static void i(String message) {
        if (!BuildConfig.DEBUG) return;
        Log.i(LOG, message);
    }

    public static void v(String message) {
        if (!BuildConfig.DEBUG) return;
        Log.v(LOG, message);
    }

}
