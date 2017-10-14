package com.naahac.tvaproject.controller.frontend;

import com.naahac.tvaproject.models.User;

public interface ProfileFrontEnd extends FrontEndBase {
    void onUserUpdateSuccess();
    void onUserUpdateError();
    void onGetUserSuccess(User user);
}
