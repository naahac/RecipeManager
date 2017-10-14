package com.naahac.tvaproject.ui.screens.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.DataStorage;
import com.naahac.tvaproject.controller.backend.LoginBackEnd;
import com.naahac.tvaproject.controller.frontend.LoginFrontEnd;
import com.naahac.tvaproject.models.User;
import com.naahac.tvaproject.ui.base.BaseActivity;
import com.naahac.tvaproject.ui.screens.main.MainActivity;
import com.naahac.tvaproject.ui.screens.main.offline_mode.OfflineModeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Natanael Ahac
 * @version 1.0
 * @date 20.3.2017
 * @comment none
 */

public class LoginActivity extends BaseActivity implements LoginFrontEnd, RegisterFragment.RegisterFragmentInterface, LoginFragment.LoginFragmentInterface{

    private LoginBackEnd mBackend;
    private DataStorage mStorage;

    @Bind(R.id.loading_spinner)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mBackend = App.getBackEndController().getLoginBackEnd(this);
        mStorage = App.getBackEndController().getDataStorage();
        SetLoginFragment();
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startOfflineModeActivity(){
        Intent intent = new Intent(this, OfflineModeActivity.class);
        startActivity(intent);
        finish();
    }

    //Login frontend methods
    @Override
    public void onLoginSuccess() {
        hideLoadingProgressBar();
        startMainActivity();
    }

    @Override
    public void onLoginError() {
        hideLoadingProgressBar();
        LoginFragment myFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag(LoginFragment.TAG);
        if (myFragment != null && myFragment.isVisible()) {
            myFragment.LoginError();
        }
    }

    @Override
    public void onRegisterSuccess() {
        hideLoadingProgressBar();
        Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show();
        SetLoginFragment();
    }

    @Override
    public void onRegisterError() {
        hideLoadingProgressBar();
        RegisterFragment myFragment = (RegisterFragment) getSupportFragmentManager().findFragmentByTag(RegisterFragment.TAG);
        if (myFragment != null && myFragment.isVisible()) {
            myFragment.RegisterError();
        }
    }

    //Base frontend methods
    @Override
    public void onNoInternet() {
        hideLoadingProgressBar();
        Toast.makeText(this, R.string.all_error_no_internet, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidToken() {
        hideLoadingProgressBar();
    }

    @Override
    public void onUnknownError() {
        hideLoadingProgressBar();
    }

    @Override
    public void Login(String username, String password) {
        showLoadingProgressBar();
        mBackend.Login(this, username, password);
    }

    @Override
    public void LoginOfflineMode() {
        startOfflineModeActivity();
    }

    @Override
    public void Register(User user) {
        showLoadingProgressBar();
        mBackend.Register(this, user);
    }

    @Override
    public void SetLoginFragment() {
        replaceFragment(new LoginFragment(), LoginFragment.TAG);
    }

    public void showLoadingProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoadingProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void SetRegisterFragment() {
        replaceFragment(new RegisterFragment(), RegisterFragment.TAG);
    }

    private void replaceFragment(Fragment fragment, String tag){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.view_login_container, fragment, tag)
                .commit();
    }
}
