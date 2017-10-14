package com.naahac.tvaproject.ui.screens.main.my_recipes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.MyRecipesBackEnd;
import com.naahac.tvaproject.controller.frontend.MyRecipesFrontEnd;
import com.naahac.tvaproject.models.Recipe;
import com.naahac.tvaproject.ui.base.BaseFragment;
import com.naahac.tvaproject.utils.Logger;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Natanael on 11. 04. 2017.
 */

public class MyRecipesFragment extends BaseFragment implements RecipesRecyclerAdapter.ItemInteractionInterface, MyRecipesFrontEnd {
    public static final String  TAG = MyRecipesFragment.class.getSimpleName();

    MyRecipesBackEnd mBackEnd;
    ArrayList<Recipe> mRecipes;

    public interface MyReceipesInterface{
        void openRecipeDetailsActivity(Recipe recipe);
        void showLoadingProgressBar();
        void hideLoadingProgressBar();
    }

    MyReceipesInterface mActivityCallback;
    RecipesRecyclerAdapter mAdapter;

    @Bind(R.id.recycler_view_recipes)
    public RecyclerView mRecyclerViewRecipes;

    @Bind(R.id.fab_recipes_add)
    public FloatingActionButton mFabAddRecipe;

    @Bind(R.id.swipe_refresh_recipes)
    SwipeRefreshLayout mSwipeRefreshLayoutRecipes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBackEnd = App.getBackEndController().getMyRecipesBackEnd(this);
        mRecipes = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my_recipes, container, false);
        ButterKnife.bind(this, layout);
        mRecyclerViewRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecipesRecyclerAdapter(mRecipes, this, getActivity());
        mRecyclerViewRecipes.setAdapter(mAdapter);
        mSwipeRefreshLayoutRecipes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mActivityCallback.showLoadingProgressBar();
                mBackEnd.GetMyRecipes(getActivity());
            }
        });
        mActivityCallback.showLoadingProgressBar();
        return layout;
    }

    @Override
    public void onStop() {
        Logger.print(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBackEnd.GetMyRecipes(getActivity());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivityCallback = (MyReceipesInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MyReceipesInterfacenaa");
        }
    }

    @OnClick(R.id.fab_recipes_add)
    public void onAddRecipeClick(){
        mActivityCallback.openRecipeDetailsActivity(null);
    }

    @Override
    public void OnItemClick(Recipe item) {
        Logger.print(TAG, "item clicked " + item.getTitle());
        mActivityCallback.openRecipeDetailsActivity(item);
    }

    @Override
    public void onNoInternet() {
        Toast.makeText(getActivity(), R.string.all_error_no_internet, Toast.LENGTH_LONG).show();
        mActivityCallback.hideLoadingProgressBar();
        mSwipeRefreshLayoutRecipes.setRefreshing(false);
    }

    @Override
    public void onInvalidToken() {
        Toast.makeText(getActivity(), R.string.all_error_token, Toast.LENGTH_LONG).show();
        mActivityCallback.hideLoadingProgressBar();
        mSwipeRefreshLayoutRecipes.setRefreshing(false);
    }

    @Override
    public void onUnknownError() {
        Toast.makeText(getActivity(), R.string.all_error_unknown, Toast.LENGTH_LONG).show();
        mActivityCallback.hideLoadingProgressBar();
        mSwipeRefreshLayoutRecipes.setRefreshing(false);
    }

    @Override
    public void onGetRecipesSuccess(ArrayList<Recipe> recipeList) {
        mActivityCallback.hideLoadingProgressBar();
        Logger.print(TAG, "swaping items: " + recipeList.size());
        mRecipes = recipeList;
        mAdapter.swapItems(mRecipes);
        mSwipeRefreshLayoutRecipes.setRefreshing(false);
    }

    @Override
    public void onGetRecipesFailure() {
        mActivityCallback.hideLoadingProgressBar();
        mSwipeRefreshLayoutRecipes.setRefreshing(false);
    }
}
