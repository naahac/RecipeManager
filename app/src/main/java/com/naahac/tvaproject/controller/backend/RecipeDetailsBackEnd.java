package com.naahac.tvaproject.controller.backend;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.network.IngredientsApi;
import com.naahac.tvaproject.controller.backend.network.MyRecipesApi;
import com.naahac.tvaproject.controller.backend.request.AddRecipeRequest;
import com.naahac.tvaproject.controller.frontend.RecipeDetailsFrontEnd;
import com.naahac.tvaproject.models.Ingredient;
import com.naahac.tvaproject.models.Nutrient;
import com.naahac.tvaproject.models.Recipe;
import com.naahac.tvaproject.utils.AndroidUtils;
import com.naahac.tvaproject.utils.GeneralUtils;
import com.naahac.tvaproject.utils.Logger;
import com.naahac.tvaproject.utils.RetrofitUtil;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RecipeDetailsBackEnd extends BackEndBase<RecipeDetailsFrontEnd> {

    private static final String TAG = RecipeDetailsBackEnd.class.getSimpleName();
    private Retrofit retrofit;
    private Retrofit retrofitIngredients;
    private Gson mGson;
    private DataStorage mStorage;

    // INIT
    public RecipeDetailsBackEnd(RecipeDetailsFrontEnd frontEnd) {
        super(frontEnd);
        this.retrofit = RetrofitUtil.getRetrofit(HttpLoggingInterceptor.Level.BODY);
        this.retrofitIngredients = RetrofitUtil.getRetrofitIngredients(HttpLoggingInterceptor.Level.BODY);
        this.mGson = GeneralUtils.getGson();
        mStorage = App.getBackEndController().getDataStorage();
    }

    // FUNCIONALITY
    public void CreateRecipe(final Context context, Recipe recipe) {
        if (!AndroidUtils.isNetworkAvailable(context)) {
            getFrontEnd().onNoInternet();
            return;
        }

        retrofit.create(MyRecipesApi.class)
                .addRecipe(new AddRecipeRequest(recipe, mStorage.getStringFromPrefs(context, DataStorage.PREF_KEY_TOKEN)))
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        switch (response.code()){
                            case HttpURLConnection.HTTP_OK:
                                getFrontEnd().onAddRecipeSuccess();
                                break;
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                getFrontEnd().onAddRecipeFailure();
                                break;
                            default:
                                getFrontEnd().onUnknownError();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Logger.print(TAG, "error");
                        getFrontEnd().onUnknownError();
                    }
                });
    }

    public void GetIngredientSuggestions(final Context context, String searchString) {
        if (!AndroidUtils.isNetworkAvailable(context)) {
            getFrontEnd().onNoInternet();
            return;
        }

        retrofitIngredients.create(IngredientsApi.class)
                .autocomplete(searchString)
                .enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        switch (response.code()){
                            case HttpURLConnection.HTTP_OK:
                                ArrayList<Ingredient> suggestions = mGson.fromJson(response.body(), new TypeToken<ArrayList<Ingredient>>(){}.getType());
                                getFrontEnd().onSuggestionResult(suggestions);
                                break;
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                getFrontEnd().onGetNutritionError();
                                break;
                            default:
                                getFrontEnd().onGetNutritionError();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Logger.print(TAG, "error");
                        getFrontEnd().onGetNutritionError();
                    }
                });
    }


    public void GetIngredientNutrition(final Context context, String usdaid) {
        if (!AndroidUtils.isNetworkAvailable(context)) {
            getFrontEnd().onNoInternet();
            return;
        }

        retrofitIngredients.create(IngredientsApi.class)
                .nutritionById(usdaid)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        switch (response.code()){
                            case HttpURLConnection.HTTP_OK:
                                ArrayList<Nutrient> nutrients= mGson.fromJson(response.body().get("nutrients"), new TypeToken<ArrayList<Nutrient>>(){}.getType());
                                getFrontEnd().onGetNutritionResult(nutrients);
                                break;
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                getFrontEnd().onUnknownError();
                                break;
                            default:
                                getFrontEnd().onUnknownError();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Logger.print(TAG, "error");
                        getFrontEnd().onUnknownError();
                    }
                });
    }

    public void DbPediaTest(){
        String sparqlQueryString1 = "PREFIX dbont: <http://dbpedia.org/ontology/> " +
                "PREFIX dbp: <http://dbpedia.org/property/>" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>" +
                "   SELECT ?musician  ?place" +
                "   WHERE { " +
                "       ?musician dbont:birthPlace ?place ." +
                "   }";

//        Query query = QueryFactory.create(sparqlQueryString1);
//        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
//
//        ResultSet results = qexec.execSelect();
//        ResultSetFormatter.out(System.out, results, query);
//
//        qexec.close() ;
    }
}
