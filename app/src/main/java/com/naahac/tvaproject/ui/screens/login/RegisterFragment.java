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
import com.naahac.tvaproject.models.User;
import com.naahac.tvaproject.ui.base.BaseFragment;
import com.naahac.tvaproject.utils.TextChecker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Natanael on 11. 04. 2017.
 */

public class RegisterFragment extends BaseFragment {
    public static final String  TAG = RegisterFragment.class.getSimpleName();

    public interface RegisterFragmentInterface {
        void Register(User user);
        void SetLoginFragment();
    }

    //Activity
    RegisterFragmentInterface mActivityCallback;

    @Bind(R.id.edit_text_register_username)
    public TextInputEditText mEditTextUsername;

    @Bind(R.id.edit_text_register_password)
    public TextInputEditText mEditTextPassword;

    @Bind(R.id.edit_text_register_password_confirm)
    public TextInputEditText mEditTextPasswordConfirm;

    @Bind(R.id.til_register_password_confirm)
    public TextInputLayout mTilPasswordConfirm;

    @Bind(R.id.edit_text_register_email)
    public TextInputEditText mEditTextEmail;

    @Bind(R.id.edit_text_register_name)
    public TextInputEditText mEditTextName;

    @Bind(R.id.edit_text_register_surname)
    public TextInputEditText mEditTextSurname;

    private boolean checkInputs(){
        boolean isValid = true;
        String username = mEditTextUsername.getText().toString();
        String password = mEditTextPassword.getText().toString();
        String passwordConfirm = mEditTextPasswordConfirm.getText().toString();
        String email = mEditTextEmail.getText().toString();
        String name = mEditTextName.getText().toString();
        String surname = mEditTextSurname.getText().toString();

        if(username.isEmpty()){
            isValid = false;
            mEditTextUsername.setError(getString(R.string.all_error_empty));
        }
        if(password.isEmpty()){
            isValid = false;
            mEditTextPassword.setError(getString(R.string.all_error_empty));
        }
        if(password.isEmpty()){
            isValid = false;
            mEditTextPassword.setError(getString(R.string.all_error_empty));
        }
        if (passwordConfirm.isEmpty()){
            isValid = false;
            mEditTextPasswordConfirm.setError(getString(R.string.all_error_empty));
        }
        if(!password.equals(passwordConfirm)){
            isValid = false;
            mEditTextPassword.setError(getString(R.string.all_error_password_dont_match));
            mEditTextPasswordConfirm.setError(getString(R.string.all_error_password_dont_match));
        }
        if(email.isEmpty()){
            isValid = false;
            mEditTextEmail.setError(getString(R.string.all_error_empty));
        }else if(!TextChecker.emailChecker(email)){
            isValid = false;
            mEditTextEmail.setError(getString(R.string.all_error_email));
        }
        if(name.isEmpty()){
            isValid = false;
            mEditTextName.setError(getString(R.string.all_error_empty));
        }
        if(surname.isEmpty()){
            isValid = false;
            mEditTextSurname.setError(getString(R.string.all_error_empty));
        }
        return isValid;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivityCallback = (RegisterFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement LoginActivityInterface");
        }
    }


    @OnClick(R.id.button_register)
    public void onRegisterClick(){
        if (!checkInputs())
            return;
        mActivityCallback.Register(new User(mEditTextUsername.getText().toString(),
                mEditTextPassword.getText().toString(),
                mEditTextEmail.getText().toString(),
                mEditTextName.getText().toString(),
                mEditTextSurname.getText().toString()));
    }

    @OnClick(R.id.button_register_back)
    public void onBackToLoginClick(){
        mActivityCallback.SetLoginFragment();
    }

    public void RegisterError(){
        Toast.makeText(getActivity(), R.string.error_register, Toast.LENGTH_LONG).show();
    }


}
