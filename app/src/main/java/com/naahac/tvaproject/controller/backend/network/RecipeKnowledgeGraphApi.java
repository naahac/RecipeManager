package com.naahac.tvaproject.controller.backend.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Natanael on 12. 03. 2017.
 */

public interface RecipeKnowledgeGraphApi {
    @GET("/v1/entities:search")
    Call<String> query(@Query("ids") String ids, @Query("query") String query, @Query("key") String apiKey, @Query("limit") Integer limit);
}
