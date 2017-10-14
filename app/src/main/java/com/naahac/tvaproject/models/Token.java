package com.naahac.tvaproject.models;

/**
 * Created by Natanael on 3. 06. 2017.
 */

public class Token {
    private String tokenId;
    private int userId;

    public Token(String tokenId, int userId) {
        this.tokenId = tokenId;
        this.userId = userId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
