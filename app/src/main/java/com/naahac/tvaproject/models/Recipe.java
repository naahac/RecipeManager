package com.naahac.tvaproject.models;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natanael on 25. 04. 2017.
 */
@Entity(indexes = {
        @Index(value = "recipeId", unique = true)
})
public class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient
    private boolean isNew;
    @Id
    private Long recipeId;
    @Transient
    private Picture picture;
    private String title;
    private String description;
    private boolean isPublic;
    @ToMany(referencedJoinProperty = "recipeId")
    private List<Ingredient> ingredients;
    @ToMany(referencedJoinProperty = "recipeId")
    private List<PreparationStep> preparationSteps;
    private int pictureId;
    private int preparationTime;
    private int userId;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1947830398)
    private transient RecipeDao myDao;

    @Keep
    public Recipe(boolean isNew) {
        this.isNew = isNew;
    }

    @Keep
    public Recipe(Long recipeId, Picture picture, String name, String description, boolean isPublic, List<Ingredient> ingredients, List<PreparationStep> steps, int preparationTime) {
        this.recipeId = recipeId;
        this.title = name;
        this.description = description;
        this.isPublic = isPublic;
        this.picture = picture;
        this.ingredients = ingredients;
        this.preparationSteps = steps;
        this.preparationTime = preparationTime;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Keep
    public List<Ingredient> getIngredientsDao() {
        if (ingredients == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            IngredientDao targetDao = daoSession.getIngredientDao();
            List<Ingredient> ingredientsNew = targetDao._queryRecipe_Ingredients(recipeId);
            synchronized (this) {
                if (ingredients == null) {
                    ingredients = ingredientsNew;
                }
            }
        }
        return ingredients;
    }

    @Keep
    public List<Ingredient> getIngredients(){
        return this.ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Keep
    public List<PreparationStep> getPreparationStepsDao() {
        if (preparationSteps == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PreparationStepDao targetDao = daoSession.getPreparationStepDao();
            List<PreparationStep> preparationStepsNew = targetDao._queryRecipe_PreparationSteps(recipeId);
            synchronized (this) {
                if (preparationSteps == null) {
                    preparationSteps = preparationStepsNew;
                }
            }
        }
        return preparationSteps;
    }

    @Keep
    public List<PreparationStep> getPreparationSteps(){
        return this.preparationSteps;
    }

    public void setPreparationSteps(ArrayList<PreparationStep> preparationSteps) {
        this.preparationSteps = preparationSteps;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean getIsNew() {
        return this.isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean getIsPublic() {
        return this.isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 183837919)
    public synchronized void resetIngredients() {
        ingredients = null;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 321435490)
    public synchronized void resetPreparationSteps() {
        preparationSteps = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    @Generated(hash = 1261907928)
    public Recipe(Long recipeId, String title, String description, boolean isPublic, int pictureId, int preparationTime, int userId) {
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.pictureId = pictureId;
        this.preparationTime = preparationTime;
        this.userId = userId;
    }

    @Generated(hash = 829032493)
    public Recipe() {
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1484851246)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRecipeDao() : null;
    }
}
