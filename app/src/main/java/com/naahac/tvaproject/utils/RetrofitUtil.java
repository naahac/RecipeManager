package com.naahac.tvaproject.utils;

import com.naahac.tvaproject.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Natanael on 12. 03. 2017.
 */

public class RetrofitUtil {
    public static final String PRODUCTION_WEB_SERVICE_URL = "https://tvaproject.herokuapp.com/";
    public static final String DEBUG_WEB_SERVICE_URL = "http://10.0.2.2:3000/";
    public static final String DEBUG_WEB_SERVICE_INGREDIENTS_URL = "http://10.0.2.2:8080/";

    public static String getBaseUrl(){
        return BuildConfig.URL_API_BASE_SERVICE;
    }
    public static String getBaseUrlIngredients(){
        return BuildConfig.URL_INGREDIENTS_API_BASE_SERVICE;
    }

    public static Retrofit getRetrofit(HttpLoggingInterceptor.Level logLevel){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request()
//                        .newBuilder()
//                        .addHeader("x-apikey", "b206aa0d572322503a384da6da2d5c31f2635")
//                        .addHeader("cache-control", "no-cache")
//                        .addHeader("content-type", "application/json")
//                        .build();
//                return chain.proceed(request);
//            }
//        });

        // add logging as last interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(logLevel);
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static Retrofit getRetrofitIngredients(HttpLoggingInterceptor.Level logLevel){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // add logging as last interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(logLevel);
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .baseUrl(getBaseUrlIngredients())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static Retrofit getRetrofit(String baseUrl, HttpLoggingInterceptor.Level logLevel){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(logLevel);
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
