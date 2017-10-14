package com.naahac.tvaproject.controller.backend.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Natanael on 12. 03. 2017.
 */

public interface IngredientsApi {
    @GET("/rest/ingredients/autocomplete/{searchString}")
    Call<JsonArray> autocomplete(@Path("searchString") String searchString);
    @GET("/rest/ingredients/id/{id}")
    Call<JsonObject> nutritionById(@Path("id") String id);
}
