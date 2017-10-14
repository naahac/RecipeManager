package com.naahac.tvaproject.controller.backend.request;

import com.naahac.tvaproject.models.Recipe;

/**
 * Created by Natanael on 12. 05. 2017.
 */

public class AddRecipeRequest {
    private Recipe recipe;
    private String tokenId;

    public AddRecipeRequest(Recipe recipe, String tokenId) {
        this.recipe = recipe;
        this.tokenId = tokenId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
