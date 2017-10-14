package com.naahac.tvaproject.controller.backend;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.naahac.tvaproject.R;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.network.MyRecipesApi;
import com.naahac.tvaproject.controller.backend.network.RecipeKnowledgeGraphApi;
import com.naahac.tvaproject.controller.frontend.MyRecipesFrontEnd;
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


public class MyRecipesBackEnd extends BackEndBase<MyRecipesFrontEnd> {

    private static final String TAG = MyRecipesBackEnd.class.getSimpleName();
    private Retrofit retrofit;
    private Gson mGson;
    private DataStorage mStorage;

    // INIT
    public MyRecipesBackEnd(MyRecipesFrontEnd frontEnd) {
        super(frontEnd);
//        this.retrofit = RetrofitUtil.getRetrofit("https://kgsearch.googleapis.com/", HttpLoggingInterceptor.Level.BODY);
        this.retrofit = RetrofitUtil.getRetrofit(HttpLoggingInterceptor.Level.BODY);
        this.mGson = GeneralUtils.getGson();
        mStorage = App.getBackEndController().getDataStorage();
    }

    // FUNCIONALITY

    public void GetMyRecipes(final Context context) {
        if (!AndroidUtils.isNetworkAvailable(context)) {
            getFrontEnd().onNoInternet();
            return;
        }

        retrofit.create(MyRecipesApi.class)
                .getRecipesByToken(mStorage.getStringFromPrefs(context, DataStorage.PREF_KEY_TOKEN))
                .enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        switch (response.code()){
                            case HttpURLConnection.HTTP_OK:
                                Logger.print(TAG, "got items");
                                ArrayList<Recipe> recipes = (ArrayList<Recipe>) mGson.fromJson(response.body().toString(), new TypeToken<ArrayList<Recipe>>(){}.getType());
                                App.getBackEndController().getDbHelper(context).saveRecipe(recipes);
                                getFrontEnd().onGetRecipesSuccess(recipes);
                                break;
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                getFrontEnd().onGetRecipesFailure();
                                break;
                            default:
                                getFrontEnd().onUnknownError();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Logger.print(TAG, "error" + t.getMessage());
                        getFrontEnd().onUnknownError();
                    }
                });
    }

    public void RecipeTest(final Context context) {
        if (!AndroidUtils.isNetworkAvailable(context)) {
            getFrontEnd().onNoInternet();
            return;
        }

        retrofit.create(RecipeKnowledgeGraphApi.class)
                .query(null, "peanut+butter", context.getString(R.string.google_knowledge_graph_key), 10)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        switch (response.code()){
                            case HttpURLConnection.HTTP_OK:
                                break;
                            case HttpURLConnection.HTTP_NOT_FOUND:

                                break;
                            default:
                                getFrontEnd().onUnknownError();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Logger.print(TAG, "error");
                        getFrontEnd().onUnknownError();
                    }
                });
    }
}
