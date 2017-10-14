package com.naahac.tvaproject.controller.backend.request;

import java.io.Serializable;

/**
 * Created by Natanael on 24. 05. 2017.
 */

public class SearchRequest extends BaseRequest implements Serializable {

    private String recipeName;
    private int minPreparationSteps;
    private int maxPreparationSteps;
    private int minIngredients;
    private int maxIngredients;
    private int minPreparationTime;
    private int maxPreparationTime;

    public SearchRequest(String token, String recipeName, int minPreparationSteps, int maxPreparationSteps, int minIngredients, int maxIngredients, int minPreparationTime, int maxPreparationTime) {
        super(token);
        this.recipeName = recipeName;
        this.minPreparationSteps = minPreparationSteps;
        this.maxPreparationSteps = maxPreparationSteps;
        this.minIngredients = minIngredients;
        this.maxIngredients = maxIngredients;
        this.minPreparationTime = minPreparationTime;
        this.maxPreparationTime = maxPreparationTime;
    }

    public SearchRequest(String token) {
        super(token);
    }

    public SearchRequest() {
        super("");
        this.recipeName = "";
        this.minPreparationSteps = 1;
        this.maxPreparationSteps = 100;
        this.minIngredients = 1;
        this.maxIngredients = 100;
        this.minPreparationTime = 1;
        this.maxPreparationTime = 100;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getMinPreparationSteps() {
        return minPreparationSteps;
    }

    public void setMinPreparationSteps(int minPreparationSteps) {
        this.minPreparationSteps = minPreparationSteps;
    }

    public int getMaxPreparationSteps() {
        return maxPreparationSteps;
    }

    public void setMaxPreparationSteps(int maxPreparationSteps) {
        this.maxPreparationSteps = maxPreparationSteps;
    }

    public int getMinIngredients() {
        return minIngredients;
    }

    public void setMinIngredients(int minIngredients) {
        this.minIngredients = minIngredients;
    }

    public int getMaxIngredients() {
        return maxIngredients;
    }

    public void setMaxIngredients(int maxIngredients) {
        this.maxIngredients = maxIngredients;
    }

    public int getMinPreparationTime() {
        return minPreparationTime;
    }

    public void setMinPreparationTime(int minPreparationTime) {
        this.minPreparationTime = minPreparationTime;
    }

    public int getMaxPreparationTime() {
        return maxPreparationTime;
    }

    public void setMaxPreparationTime(int maxPreparationTime) {
        this.maxPreparationTime = maxPreparationTime;
    }
}
