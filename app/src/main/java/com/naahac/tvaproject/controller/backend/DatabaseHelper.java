package com.naahac.tvaproject.controller.backend;

import android.content.Context;

import com.naahac.tvaproject.models.DaoMaster;
import com.naahac.tvaproject.models.Ingredient;
import com.naahac.tvaproject.models.IngredientDao;
import com.naahac.tvaproject.models.PreparationStep;
import com.naahac.tvaproject.models.PreparationStepDao;
import com.naahac.tvaproject.models.Recipe;
import com.naahac.tvaproject.models.RecipeDao;

import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;

/**
 * Created by Natanael on 25. 05. 2017.
 */

public class DatabaseHelper {

    private Context mContext;
    private AbstractDaoSession daoSession;
    private RecipeDao recipeDao;
    private IngredientDao ingredientDao;
    private PreparationStepDao stepDao;

    public DatabaseHelper(Context mContext) {
        this.mContext = mContext;
        newDaoSession(mContext);
    }

    private void newDaoSession(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public RecipeDao getRecipeDao(){
        if(recipeDao == null)
            recipeDao = (RecipeDao) daoSession.getDao(Recipe.class);
        return recipeDao;
    }

    public IngredientDao getIngredientDao(){
        if(ingredientDao == null)
            ingredientDao = (IngredientDao) daoSession.getDao(Ingredient.class);
        return ingredientDao;
    }

    public PreparationStepDao getPreparationStepDao(){
        if(stepDao == null)
            stepDao = (PreparationStepDao) daoSession.getDao(PreparationStep.class);
        return stepDao;
    }

    public void saveRecipe(ArrayList<Recipe> recipes){
        getRecipeDao().insertOrReplaceInTx(recipes);
        for (Recipe r :
                recipes) {

                deleteIngredients(r.getRecipeId());
                deleteSteps(r.getRecipeId());
                getIngredientDao().insertOrReplaceInTx(r.getIngredients());
                getPreparationStepDao().insertOrReplaceInTx(r.getPreparationSteps());
        }
    }

    private void deleteIngredients(Long recipeId){
        for (Ingredient i : getIngredientDao().queryBuilder().where(IngredientDao.Properties.RecipeId.eq(recipeId)).list()) {
            getIngredientDao().delete(i);
        }
    }

    private void deleteSteps(Long recipeId){
        for (PreparationStep s : getPreparationStepDao().queryBuilder().where(PreparationStepDao.Properties.RecipeId.eq(recipeId)).list()) {
            getPreparationStepDao().delete(s);
        }
    }
}
