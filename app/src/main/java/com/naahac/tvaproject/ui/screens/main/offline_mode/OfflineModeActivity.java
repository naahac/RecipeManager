package com.naahac.tvaproject.ui.screens.main.offline_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.DataStorage;
import com.naahac.tvaproject.models.Ingredient;
import com.naahac.tvaproject.models.IngredientDao;
import com.naahac.tvaproject.models.PreparationStep;
import com.naahac.tvaproject.models.PreparationStepDao;
import com.naahac.tvaproject.models.Recipe;
import com.naahac.tvaproject.ui.base.BaseActivity;
import com.naahac.tvaproject.ui.screens.login.LoginActivity;
import com.naahac.tvaproject.ui.screens.main.my_recipes.MyRecipesFragment;
import com.naahac.tvaproject.ui.screens.main.my_recipes.RecipesRecyclerAdapter;
import com.naahac.tvaproject.ui.screens.recipe_details.RecipeDetailsActivity;
import com.naahac.tvaproject.utils.Logger;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Natanael on 13. 04. 2017.
 */

public class OfflineModeActivity extends BaseActivity implements MyRecipesFragment.MyReceipesInterface, RecipesRecyclerAdapter.ItemInteractionInterface {

    public static final String TAG = OfflineModeActivity.class.getSimpleName();

    DataStorage mDataStorage;
    ArrayList<Recipe> mRecipes;

    @Bind(R.id.toolbar_offline_mode)
    public Toolbar mToolbar;
    @Bind(R.id.recycler_view_recipes)
    RecyclerView mRecyclerView;
    RecipesRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataStorage = App.getBackEndController().getDataStorage();
            setContentView(R.layout.activity_offline_mode);
            ButterKnife.bind(this);
            Logger.print(TAG, "onCreate");
        mToolbar.setTitle("Stored recipes");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecipes = new ArrayList<>();
        mRecipes = (ArrayList<Recipe>) App.getBackEndController().getDbHelper(this).getRecipeDao().queryBuilder().list();
        for (Recipe r :
                mRecipes) {
            r.setIngredients((ArrayList<Ingredient>) App
                    .getBackEndController()
                    .getDbHelper(this)
                    .getIngredientDao().queryBuilder()
                    .where(IngredientDao.Properties.RecipeId.eq(r.getRecipeId()))
                    .list());
            r.setPreparationSteps((ArrayList<PreparationStep>) App
                    .getBackEndController()
                    .getDbHelper(this)
                    .getPreparationStepDao()
                    .queryBuilder()
                    .where(PreparationStepDao.Properties.RecipeId.eq(r.getRecipeId()))
                    .list());

        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecipesRecyclerAdapter(mRecipes, this, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.offline_mode_menu, menu);
        return true;
    }

    private void goOnline(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
            case android.R.id.home:
                goOnline();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void openRecipeDetailsActivity(Recipe recipe) {
        Intent intent = new Intent(this,RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.RECIPE_KEY, recipe);
        intent.putExtra(RecipeDetailsActivity.IS_EDITABLE_KEY, false);
        startActivity(intent);
    }

    @Override
    public void showLoadingProgressBar() {
        //not needed
    }

    @Override
    public void hideLoadingProgressBar() {
        //not needed
    }

    @Override
    public void OnItemClick(Recipe item) {
        openRecipeDetailsActivity(item);
    }
}
