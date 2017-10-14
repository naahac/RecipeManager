package com.naahac.tvaproject.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by Natanael on 25. 04. 2017.
 */
@Entity
public class PreparationStep implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long preparationStepId;
    private String Description;
    private Long recipeId;

    @Generated(hash = 862388419)
    public PreparationStep(Long preparationStepId, String Description,
            Long recipeId) {
        this.preparationStepId = preparationStepId;
        this.Description = Description;
        this.recipeId = recipeId;
    }

    public PreparationStep() {

    }

    public Long getPreparationStepId() {
        return preparationStepId;
    }

    public void setPreparationStepId(Long preparationStepId) {
        this.preparationStepId = preparationStepId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public Long getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}
