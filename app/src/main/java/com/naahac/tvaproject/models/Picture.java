package com.naahac.tvaproject.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Natanael on 19. 05. 2017.
 */
@Entity
public class Picture {
    private static final long serialVersionUID = 1L;

    @Id
    private
    int pictureId;
    private String base64Data;
    private Long recipeId;

    @Generated(hash = 2146838951)
    public Picture(int pictureId, String base64Data, Long recipeId) {
        this.pictureId = pictureId;
        this.base64Data = base64Data;
        this.recipeId = recipeId;
    }

    @Generated(hash = 1602548376)
    public Picture() {
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}
