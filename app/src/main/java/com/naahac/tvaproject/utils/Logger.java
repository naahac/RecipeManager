package com.naahac.tvaproject.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class Logger {
    private static boolean PRINT_LOG = true;//BuildConfig.DEBUG;

    public static void print(String TAG, String... message){
        if(!PRINT_LOG) return;
        for(String item : message)
            if(item != null)
                Log.v(TAG, item);
    }

    public static void printError(String TAG, String... message){
        if(!PRINT_LOG) return;
        for(String item : message)
            if(item != null)
                Log.e(TAG, item);
    }

    public static void printJSON(String TAG, JSONObject json){
        if(!PRINT_LOG) return;
        try {
            Log.v(TAG, json.toString(4));
        } catch (JSONException e) {
            Log.v(TAG, "Error parsing JSON object");
        }
    }

    public static void printJSON(String TAG, Object object){
        if(!PRINT_LOG) return;
        try {
            Log.v(TAG, new JSONObject(GeneralUtils.getGson().toJson(object)).toString(4));
        } catch (JSONException e) {
            Log.v(TAG, "Error parsing JSON object");
        }
    }

    public static void printLongJSON(String TAG, JSONObject json){
        if(!PRINT_LOG) return;
        Iterator<String> keys = json.keys();
        while (keys.hasNext()){
            try {
                String key = keys.next();
                Object value = json.get(key);
                if(value instanceof JSONObject)
                    Log.v(key, ((JSONObject)value).toString(4));
                else if (value instanceof JSONArray){
                    Log.v(key, ((JSONArray)value).toString(4));
                }else
                    Log.v(key, String.valueOf(value));
            } catch (JSONException e) {
                Log.v(TAG, "Error parsing JSON object");
            }
        }
    }

    public static void setPrintLog(boolean printLog) {
        PRINT_LOG = printLog;
    }
}
