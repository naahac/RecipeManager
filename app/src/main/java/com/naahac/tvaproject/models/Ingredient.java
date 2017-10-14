package com.naahac.tvaproject.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by Natanael on 25. 04. 2017.
 */
@Entity
public class Ingredient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long ingredientId;
    private String name;
    @Transient
    private String usdaNdbId;
    private String amount;
    private Long recipeId;

    public Ingredient() {

    }

    public Ingredient(String name, String usdaNdbId) {
        this.name = name;
        this.usdaNdbId = usdaNdbId;
    }

    @Generated(hash = 2140006827)
    public Ingredient(Long ingredientId, String name, String amount, Long recipeId) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.amount = amount;
        this.recipeId = recipeId;
    }

    @Keep
    public Ingredient(Long ingredientId, String name, String amount, Long recipeId, String usdaNdbId) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.amount = amount;
        this.recipeId = recipeId;
        this.usdaNdbId = usdaNdbId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getUsdaNdbId() {
        return usdaNdbId;
    }

    public void setUsdaNdbId(String usdaNdbId) {
        this.usdaNdbId = usdaNdbId;
    }
}
