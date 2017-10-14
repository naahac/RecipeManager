package com.naahac.tvaproject.controller.backend.network;

import com.google.gson.JsonElement;
import com.naahac.tvaproject.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Natanael on 12. 03. 2017.
 */

public interface AuthenticationApi {
    @POST("/login")
    Call<JsonElement> login(@Body User user);
    @POST("users/register")
    Call<String> register(@Body User user);
}
