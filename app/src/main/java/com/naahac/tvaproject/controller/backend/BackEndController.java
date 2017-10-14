package com.naahac.tvaproject.controller.backend;

import android.content.Context;

import com.naahac.tvaproject.controller.frontend.LoginFrontEnd;
import com.naahac.tvaproject.controller.frontend.MyRecipesFrontEnd;
import com.naahac.tvaproject.controller.frontend.ProfileFrontEnd;
import com.naahac.tvaproject.controller.frontend.RecipeDetailsFrontEnd;
import com.naahac.tvaproject.controller.frontend.SearchFrontEnd;

public class BackEndController {

    public BackEndController() {
    }

    private LoginBackEnd loginBackEnd;
    private MyRecipesBackEnd myRecipesBackEnd;
    private ProfileBackEnd profileBackEnd;
    private SearchBackEnd searchBackEnd;
    RecipeDetailsBackEnd recipeDetailsBackEnd;
    private DataStorage dataStorage;
    private DatabaseHelper dbHelper;

    public DataStorage getDataStorage() {
        if(dataStorage == null)
            dataStorage = new DataStorage();
        return dataStorage;
    }

    public DatabaseHelper getDbHelper(Context context) {
        if(dbHelper == null)
            dbHelper = new DatabaseHelper(context);
        return dbHelper;
    }

    public LoginBackEnd getLoginBackEnd(LoginFrontEnd frontEnd) {
        if(loginBackEnd == null)
            loginBackEnd = new LoginBackEnd(frontEnd);
        else
            loginBackEnd.setFrontEnd(frontEnd);
        return loginBackEnd;
    }

    public MyRecipesBackEnd getMyRecipesBackEnd(MyRecipesFrontEnd frontEnd) {
        if(myRecipesBackEnd == null)
            myRecipesBackEnd = new MyRecipesBackEnd(frontEnd);
        else
            myRecipesBackEnd.setFrontEnd(frontEnd);
        return myRecipesBackEnd;
    }

    public RecipeDetailsBackEnd getRecipeDetailsBackEnd(RecipeDetailsFrontEnd frontEnd) {
        if(recipeDetailsBackEnd == null)
            recipeDetailsBackEnd = new RecipeDetailsBackEnd(frontEnd);
        else
            recipeDetailsBackEnd.setFrontEnd(frontEnd);
        return recipeDetailsBackEnd;
    }

    public ProfileBackEnd getProfileBackEnd(ProfileFrontEnd frontEnd) {
        if(profileBackEnd == null)
            profileBackEnd = new ProfileBackEnd(frontEnd);
        else
            profileBackEnd.setFrontEnd(frontEnd);
        return profileBackEnd;
    }

    public SearchBackEnd getSearchBackEnd(SearchFrontEnd frontEnd) {
        if(searchBackEnd == null)
            searchBackEnd = new SearchBackEnd(frontEnd);
        else
            searchBackEnd.setFrontEnd(frontEnd);
        return searchBackEnd;
    }
}
