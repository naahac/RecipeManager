package com.naahac.tvaproject.controller.backend;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.network.AuthenticationApi;
import com.naahac.tvaproject.controller.frontend.LoginFrontEnd;
import com.naahac.tvaproject.models.Token;
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


public class LoginBackEnd extends BackEndBase<LoginFrontEnd> {

    private static final String TAG = LoginBackEnd.class.getSimpleName();
    private Retrofit retrofit;
    private Gson mGson;
    private DataStorage mStorage;

    // INIT
    public LoginBackEnd(LoginFrontEnd frontEnd) {
        super(frontEnd);
        this.retrofit = RetrofitUtil.getRetrofit(HttpLoggingInterceptor.Level.BODY);
        this.mGson = GeneralUtils.getGson();
        mStorage = App.getBackEndController().getDataStorage();
    }

    // FUNCIONALITY
    public void Login(final Context context, final String username, final String password) {
        if (!AndroidUtils.isNetworkAvailable(context)) {
            getFrontEnd().onNoInternet();
            return;
        }

        retrofit.create(AuthenticationApi.class)
                .login(new User(username, password))
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        switch (response.code()){
                            case HttpURLConnection.HTTP_OK:
                                Logger.print(TAG, "success!");
                                mStorage.saveLoginData(context, username, password);
                                Token token = mGson.fromJson(response.body().toString(), Token.class);
                                mStorage.saveString(context, DataStorage.PREF_KEY_TOKEN, token.getTokenId());
                                mStorage.saveInteger(context, DataStorage.PREF_KEY_USER_ID, token.getUserId());
                                getFrontEnd().onLoginSuccess();
                                break;
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                getFrontEnd().onLoginError();
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

    public void Register(Context context, User user) {
        if (!AndroidUtils.isNetworkAvailable(context)) {
            getFrontEnd().onNoInternet();
            return;
        }

        retrofit.create(AuthenticationApi.class)
                .register(user)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        switch (response.code()){
                            case HttpURLConnection.HTTP_OK:
                                Logger.print(TAG, "success!");
                                getFrontEnd().onRegisterSuccess();
                                break;
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                getFrontEnd().onRegisterSuccess();
                                break;
                            default:
                                Logger.print(TAG, "error!");
                                getFrontEnd().onUnknownError();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Logger.print(TAG, "error!");
                        getFrontEnd().onUnknownError();
                    }
                });
    }
}
