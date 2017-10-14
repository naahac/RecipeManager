package com.naahac.tvaproject.controller.backend;

import android.content.Context;
import com.naahac.tvaproject.utils.GeneralUtils;
import com.naahac.tvaproject.utils.SharedPrefsUtils;

public class DataStorage {
    public static final String PREF_KEY_USERNAME = "PREF_KEY_USERNAME";
    public static final String PREF_KEY_PASSWORD = "PREF_KEY_PASSWORD";
    public static final String PREF_KEY_TOKEN = "PREF_KEY_TOKEN";
    public static final String PREF_KEY_USER_ID = "PREF_KEY_USER_ID";

    public DataStorage() {

    }

    public boolean saveLoginData(Context context, String username, String password) {
        return SharedPrefsUtils.saveData(context, GeneralUtils.getGson(), PREF_KEY_USERNAME, username) &&
                SharedPrefsUtils.saveData(context, GeneralUtils.getGson(), PREF_KEY_PASSWORD, password);
    }

    public boolean saveString(Context context,String prefkey, String stringToSave) {
        return SharedPrefsUtils.saveData(context, GeneralUtils.getGson(), prefkey, stringToSave);
    }

    public String getStringFromPrefs(Context context, String prefkey){
        return SharedPrefsUtils.getSavedData(context, GeneralUtils.getGson(), prefkey, String.class);
    }

    public boolean saveInteger(Context context,String prefkey, int integerToSave) {
        return SharedPrefsUtils.saveData(context, GeneralUtils.getGson(), prefkey, integerToSave);
    }

    public int getIntFromPrefs(Context context, String prefkey){
        return SharedPrefsUtils.getSavedData(context, GeneralUtils.getGson(), prefkey, int.class);
    }

    public boolean resetLoginData(Context context){
        return saveLoginData(context, "", "");
    }

}
