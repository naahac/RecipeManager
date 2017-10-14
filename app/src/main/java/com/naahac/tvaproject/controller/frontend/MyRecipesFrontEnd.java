package com.naahac.tvaproject.controller.frontend;

import com.naahac.tvaproject.models.Recipe;

import java.util.ArrayList;

public interface MyRecipesFrontEnd extends FrontEndBase {
    void onGetRecipesSuccess(ArrayList<Recipe> recipeList);
    void onGetRecipesFailure();
}
