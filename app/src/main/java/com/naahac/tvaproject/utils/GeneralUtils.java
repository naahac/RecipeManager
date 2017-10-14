package com.naahac.tvaproject.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GeneralUtils {
    private static Gson gson;

    public static Gson getGson(){
        if(gson == null)
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson;
    }
}
