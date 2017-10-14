package com.naahac.tvaproject.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 Created by Katja on 13.10.2016.
 */

public class AndroidUtils {
    public static final String TAG = AndroidUtils.class.getSimpleName();
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showToast(Context context, @StringRes int resId, int length){
        context = context.getApplicationContext();
        if (context == null) {
            return;
        }
        Toast.makeText(context, resId, length).show();
    }

    public static void showToast(Context context, String text, int length){
        context = context.getApplicationContext();
        if (context == null) {
            return;
        }
        Toast.makeText(context, text, length).show();
    }

}
