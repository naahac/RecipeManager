package com.naahac.tvaproject.ui.screens.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.DataStorage;
import com.naahac.tvaproject.controller.backend.LoginBackEnd;
import com.naahac.tvaproject.controller.frontend.LoginFrontEnd;
import com.naahac.tvaproject.ui.base.BaseActivity;
import com.naahac.tvaproject.ui.screens.login.LoginActivity;
import com.naahac.tvaproject.ui.screens.main.MainActivity;
import com.naahac.tvaproject.utils.Logger;

/**
 * Created by Natanael on 26. 04. 2017.
 */

public class SplashActivity extends BaseActivity implements LoginFrontEnd {

    LoginBackEnd mBackEnd;
    DataStorage mStorage;
    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mBackEnd = App.getBackEndController().getLoginBackEnd(this);
        mStorage = App.getBackEndController().getDataStorage();
        progressBar = (ProgressBar)findViewById(R.id.loading_spinner);
        readLoginDataFromPrefs();

    }

    private void readLoginDataFromPrefs(){
        String username = mStorage.getStringFromPrefs(this, DataStorage.PREF_KEY_USERNAME);
        String password = mStorage.getStringFromPrefs(this, DataStorage.PREF_KEY_PASSWORD);
        if(username == null || password == null || username.equals("") || password.equals(""))
            startLoginActivity();
        else{
            progressBar.setVisibility(View.VISIBLE);
            mBackEnd.Login(this,username,password);
        }
    }

    private void startLoginActivity(){
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.print("SplashScreen", "onResume");
    }

    private void startMainActivity(){
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginSuccess() {
        startMainActivity();
    }

    @Override
    public void onLoginError() {
        startLoginActivity();
    }

    @Override
    public void onRegisterSuccess() {
        //Not implemented
    }

    @Override
    public void onRegisterError() {
        //Not implemented
    }

    @Override
    public void onNoInternet() {
        startLoginActivity();
    }

    @Override
    public void onInvalidToken() {
        startLoginActivity();
    }

    @Override
    public void onUnknownError() {
        startLoginActivity();
    }
}
