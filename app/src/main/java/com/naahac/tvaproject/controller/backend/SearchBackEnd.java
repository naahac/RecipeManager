package com.naahac.tvaproject.controller.backend;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.network.MyRecipesApi;
import com.naahac.tvaproject.controller.backend.request.SearchRequest;
import com.naahac.tvaproject.controller.frontend.SearchFrontEnd;
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


public class SearchBackEnd extends BackEndBase<SearchFrontEnd> {

    private static final String TAG = SearchBackEnd.class.getSimpleName();
    private Retrofit retrofit;
    private Gson mGson;
    private DataStorage mStorage;

    // INIT
    public SearchBackEnd(SearchFrontEnd frontEnd) {
        super(frontEnd);
        this.retrofit = RetrofitUtil.getRetrofit(HttpLoggingInterceptor.Level.BODY);
        this.mGson = GeneralUtils.getGson();
        mStorage = App.getBackEndController().getDataStorage();
    }

    // FUNCIONALITY
    public void Search(final Context context, SearchRequest request) {
        if (!AndroidUtils.isNetworkAvailable(context)) {
            getFrontEnd().onNoInternet();
            return;
        }
        request.setTokenId(mStorage.getStringFromPrefs(context, DataStorage.PREF_KEY_TOKEN));

        retrofit.create(MyRecipesApi.class)
                .search(request)
                .enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        switch (response.code()){
                            case HttpURLConnection.HTTP_OK:
                                Logger.print(TAG, "got items");
                                ArrayList<Recipe> recipes = (ArrayList<Recipe>) mGson.fromJson(response.body().toString(), new TypeToken<ArrayList<Recipe>>(){}.getType());
                                getFrontEnd().onSearchSuccess(recipes);
                                App.getBackEndController().getDbHelper(context).saveRecipe(recipes);
                                break;
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                getFrontEnd().onSearchError();
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
}
