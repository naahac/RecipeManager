package com.naahac.tvaproject.controller.backend.request;

import java.io.Serializable;

/**
 * Created by Natanael on 12. 05. 2017.
 */

public class BaseRequest implements Serializable{
    private String tokenId;

    public BaseRequest(String token) {
        this.tokenId = token;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
