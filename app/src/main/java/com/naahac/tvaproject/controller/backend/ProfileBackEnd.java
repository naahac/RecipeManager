package com.naahac.tvaproject.controller.backend;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.network.UserApi;
import com.naahac.tvaproject.controller.frontend.ProfileFrontEnd;
import com.naahac.tvaproject.models.User;
import com.naahac.tvaproject.utils.AndroidUtils;
import com.naahac.tvaproject.utils.GeneralUtils;
import com.naahac.tvaproject.utils.Logger;
import com.naahac.tvaproject.utils.RetrofitUtil;

import java.net.HttpURLConnection;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProfileBackEnd extends BackEndBase<ProfileFrontEnd> {

    private static final String TAG = ProfileBackEnd.class.getSimpleName();
    private Retrofit retrofit;
    private Gson mGson;
    private DataStorage mStorage;

    // INIT
    public ProfileBackEnd(ProfileFrontEnd frontEnd) {
        super(frontEnd);
        this.retrofit = RetrofitUtil.getRetrofit(HttpLoggingInterceptor.Level.BODY);
        this.mGson = GeneralUtils.getGson();
        mStorage = App.getBackEndController().getDataStorage();
    }

    // FUNCIONALITY
    public void UpdateUser(Context context, User user) {
        if (!AndroidUtils.isNetworkAvailable(context)) {
            getFrontEnd().onNoInternet();
            return;
        }

        retrofit.create(UserApi.class)
                .updateUser(mStorage.getStringFromPrefs(context, DataStorage.PREF_KEY_TOKEN), user)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        switch (response.code()){
                            case HttpURLConnection.HTTP_OK:
                                Logger.print(TAG, "success!");
                                getFrontEnd().onUserUpdateSuccess();
                                break;
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                getFrontEnd().onUserUpdateError();
                                break;
                            default:
                                Logger.print(TAG, "error!");
                                getFrontEnd().onUnknownError();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Logger.print(TAG, "error!");
                        getFrontEnd().onUnknownError();
                    }
                });
    }

    public void GetUser(Context context) {
        if (!AndroidUtils.isNetworkAvailable(context)) {
            getFrontEnd().onNoInternet();
            return;
        }

        retrofit.create(UserApi.class)
                .getUser(mStorage.getStringFromPrefs(context, DataStorage.PREF_KEY_TOKEN))
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        switch (response.code()){
                            case HttpURLConnection.HTTP_OK:
                                Logger.print(TAG, "success!");
                                User user = mGson.fromJson(response.body().toString(), User.class);
                                getFrontEnd().onGetUserSuccess(user);
                                break;
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                getFrontEnd().onUnknownError();
                                break;
                            default:
                                Logger.print(TAG, "error!");
                                getFrontEnd().onUnknownError();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Logger.print(TAG, "error!");
                        getFrontEnd().onUnknownError();
                    }
                });
    }
}
