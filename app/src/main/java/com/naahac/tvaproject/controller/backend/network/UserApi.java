package com.naahac.tvaproject.controller.backend.network;

import com.google.gson.JsonElement;
import com.naahac.tvaproject.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Natanael on 12. 03. 2017.
 */

public interface UserApi {
    @GET("users")
    Call<JsonElement> getUser(@Query("tokenId") String token);
    @PUT("users")
    Call<JsonElement> updateUser(@Query("tokenId") String token, @Body User user);
}
