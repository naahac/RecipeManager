package com.naahac.tvaproject.controller.frontend;

import com.naahac.tvaproject.models.Ingredient;
import com.naahac.tvaproject.models.Nutrient;

import java.util.ArrayList;

public interface RecipeDetailsFrontEnd extends FrontEndBase {
    void onAddRecipeSuccess();
    void onAddRecipeFailure();
    void onSuggestionResult(ArrayList<Ingredient> ingredients);
    void onGetNutritionResult(ArrayList<Nutrient> nutrients);
    void onGetNutritionError();
}
