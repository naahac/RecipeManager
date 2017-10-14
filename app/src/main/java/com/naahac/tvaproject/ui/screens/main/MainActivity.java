package com.naahac.tvaproject.ui.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.DataStorage;
import com.naahac.tvaproject.models.Recipe;
import com.naahac.tvaproject.ui.base.BaseActivity;
import com.naahac.tvaproject.ui.screens.login.LoginActivity;
import com.naahac.tvaproject.ui.screens.main.my_recipes.MyRecipesFragment;
import com.naahac.tvaproject.ui.screens.main.profile.ProfileFragment;
import com.naahac.tvaproject.ui.screens.main.search.SearchFragment;
import com.naahac.tvaproject.ui.screens.recipe_details.RecipeDetailsActivity;
import com.naahac.tvaproject.utils.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Natanael on 13. 04. 2017.
 */

public class MainActivity extends BaseActivity implements MyRecipesFragment.MyReceipesInterface,
        ProfileFragment.ProfileFragmentInterface, SearchFragment.SearchInterface {


    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.tab_layout_main)
    TabLayout mTabLayout;

    @Bind(R.id.viewpager_main)
    ViewPager mViewPager;
    @Bind(R.id.loading_spinner)
    ProgressBar mProgressBar;

    DataStorage mDataStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataStorage = App.getBackEndController().getDataStorage();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), MainActivity.this));
        mTabLayout.setupWithViewPager(mViewPager);
        Logger.print(TAG, "onCreate with Internet");
    }

    @Override
    public void openRecipeDetailsActivity(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.RECIPE_KEY, recipe);
        intent.putExtra(RecipeDetailsActivity.IS_EDITABLE_KEY, true);
        startActivity(intent);
    }

    @Override
    public void showLoadingProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void logout() {
        mDataStorage.resetLoginData(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}
