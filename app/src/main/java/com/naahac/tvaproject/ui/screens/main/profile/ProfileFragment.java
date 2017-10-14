package com.naahac.tvaproject.ui.screens.main.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.ProfileBackEnd;
import com.naahac.tvaproject.controller.frontend.ProfileFrontEnd;
import com.naahac.tvaproject.models.User;
import com.naahac.tvaproject.ui.base.BaseFragment;
import com.naahac.tvaproject.utils.Logger;
import com.naahac.tvaproject.utils.TextChecker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Natanael on 11. 04. 2017.
 */

public class ProfileFragment extends BaseFragment implements ProfileFrontEnd {
    public static final String  TAG = ProfileFragment.class.getSimpleName();

    ProfileBackEnd mBackEnd;

    public interface ProfileFragmentInterface{
        void logout();
        void showLoadingProgressBar();
        void hideLoadingProgressBar();
    }

    ProfileFragmentInterface mActivityCallback;

    @Bind(R.id.edit_text_register_username)
    public TextInputEditText mEditTextUsername;

    @Bind(R.id.edit_text_register_email)
    public TextInputEditText mEditTextEmail;

    @Bind(R.id.edit_text_register_name)
    public TextInputEditText mEditTextName;

    @Bind(R.id.edit_text_register_surname)
    public TextInputEditText mEditTextSurname;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBackEnd = App.getBackEndController().getProfileBackEnd(this);
        mBackEnd.GetUser(getActivity());
    }

    private boolean checkInputs(){
        boolean isValid = true;
        String email = mEditTextEmail.getText().toString();
        String name = mEditTextName.getText().toString();
        String surname = mEditTextSurname.getText().toString();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @OnClick(R.id.button_save)
    public void onSave(){
        if(!checkInputs())
            return;
        mBackEnd.UpdateUser(getActivity(), new User(mEditTextName.getText().toString(), mEditTextSurname.getText().toString(), mEditTextEmail.getText().toString()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivityCallback = (ProfileFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ProfileFragmentInterface");
        }
    }

    @OnClick(R.id.button_logout)
    public void logout(){
        mActivityCallback.logout();
    }

    @Override
    public void onNoInternet() {
        Toast.makeText(getActivity(), R.string.all_error_no_internet, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInvalidToken() {
        Toast.makeText(getActivity(), R.string.all_error_token, Toast.LENGTH_LONG).show();    }

    @Override
    public void onUnknownError() {
        Toast.makeText(getActivity(), R.string.all_error_unknown, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUserUpdateSuccess() {
        Toast.makeText(getActivity(), R.string.user_update_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUserUpdateError() {
        Toast.makeText(getActivity(), R.string.error_update_user, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetUserSuccess(User user) {
        Logger.print(TAG, "got user in fragment!");
        mEditTextName.setText(user.getName());
        mEditTextSurname.setText(user.getSurname());
        mEditTextUsername.setText(user.getUsername());
        mEditTextEmail.setText(user.getEmail());
    }
}
