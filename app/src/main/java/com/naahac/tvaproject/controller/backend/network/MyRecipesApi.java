package com.naahac.tvaproject.controller.backend.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.naahac.tvaproject.controller.backend.request.AddRecipeRequest;
import com.naahac.tvaproject.controller.backend.request.SearchRequest;
import com.naahac.tvaproject.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Natanael on 12. 03. 2017.
 */

public interface MyRecipesApi {
    @POST("/")
    Call<JsonObject> search(@Body User user);
    @PUT("recipes/")
    Call<JsonObject> addRecipe(@Body AddRecipeRequest recipeRequest);
    @GET("recipes/{token}")
    Call<JsonArray> getRecipesByToken(@Path("token") String token);
    @POST("recipes/search")
    Call<JsonArray> search(@Body SearchRequest request);
}
