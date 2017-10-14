package com.naahac.tvaproject.controller.frontend;

public interface LoginFrontEnd extends FrontEndBase {
    void onLoginSuccess();
    void onLoginError();
    void onRegisterSuccess();
    void onRegisterError();
}
