package com.naahac.tvaproject.controller.frontend;

import com.naahac.tvaproject.models.Recipe;

import java.util.ArrayList;

public interface SearchFrontEnd extends FrontEndBase {
    void onSearchSuccess(ArrayList<Recipe> recipes);
    void onSearchError();
}
