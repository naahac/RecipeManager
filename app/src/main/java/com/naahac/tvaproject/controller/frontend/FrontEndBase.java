package com.naahac.tvaproject.controller.frontend;

public interface FrontEndBase {
    void onNoInternet();
    void onInvalidToken();
    void onUnknownError();
}
