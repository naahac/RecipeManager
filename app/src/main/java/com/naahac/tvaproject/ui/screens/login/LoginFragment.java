package com.naahac.tvaproject.ui.screens.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.ui.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Natanael on 11. 04. 2017.
 */

public class LoginFragment extends BaseFragment {
    public static final String  TAG = LoginFragment.class.getSimpleName();

    interface LoginFragmentInterface{
        void Login(String username, String password);
        void LoginOfflineMode();
        void SetRegisterFragment();
    }

    //Activity
    LoginFragmentInterface mActivityCallback;

    @Bind(R.id.editText_login_username)
    public TextInputEditText mEditTextUsername;

    @Bind(R.id.editText_login_password)
    public TextInputEditText mEditTextPassword;

    @Bind(R.id.til_login_username)
    public TextInputLayout mTilUsername;

    @Bind(R.id.til_login_password)
    public TextInputLayout mTilPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivityCallback = (LoginFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnLogin");
        }
    }

    @OnClick(R.id.button_login_fragment_login)
    public void onLoginClick(){
        if (checkInputs())
            mActivityCallback.Login(mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString());
    }

    @OnClick(R.id.button_login_fragment_offline_mode)
    public void onOfflineClick(){
        mActivityCallback.LoginOfflineMode();
    }

    @OnClick(R.id.button_login_fragment_register)
    public void onRegisterClick(){
        mActivityCallback.SetRegisterFragment();
    }

    public void LoginError(){
        Toast.makeText(getActivity(), R.string.error_login, Toast.LENGTH_LONG).show();
    }

    private boolean checkInputs(){
        boolean isValid = true;
        String username = mEditTextUsername.getText().toString();
        String password = mEditTextPassword.getText().toString();

        if(username.isEmpty()){
            isValid = false;
            mEditTextUsername.setError(getString(R.string.all_error_empty));
        }
        if(password.isEmpty()){
            isValid = false;
            mEditTextPassword.setError(getString(R.string.all_error_empty));
        }
        return isValid;
    }
}
