package com.naahac.tvaproject.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.naahac.tvaproject.controller.backend.BackEndController;

/**
 * @author Natanael Ahac
 * @version 1.0
 * @date 20.3.2017
 * @comment none
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

    private static BackEndController backEndController;

    public static BackEndController getBackEndController() {
        if (backEndController == null) {
            backEndController = new BackEndController();
        }
        return backEndController;
    }
}
