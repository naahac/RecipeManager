package com.naahac.tvaproject.ui.screens.main.search;

import android.content.Context;
import android.content.Intent;
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
import com.naahac.tvaproject.controller.backend.SearchBackEnd;
import com.naahac.tvaproject.controller.backend.request.SearchRequest;
import com.naahac.tvaproject.controller.frontend.SearchFrontEnd;
import com.naahac.tvaproject.models.Recipe;
import com.naahac.tvaproject.ui.base.BaseFragment;
import com.naahac.tvaproject.ui.screens.main.my_recipes.RecipesRecyclerAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Natanael on 11. 04. 2017.
 */

public class SearchFragment extends BaseFragment implements SearchFrontEnd, RecipesRecyclerAdapter.ItemInteractionInterface {
    public static final String  TAG = SearchFragment.class.getSimpleName();

    public interface SearchInterface{
        void openRecipeDetailsActivity(Recipe recipe);
        void showLoadingProgressBar();
        void hideLoadingProgressBar();
    }

    SearchInterface mActivityCallback;
    SearchRequest mCurrentSearchRequest;

    @Bind(R.id.fab_recipes_search)
    public FloatingActionButton mFabSearch;

    SearchBackEnd mBackEnd;
    ArrayList<Recipe> mRecipes;

    RecipesRecyclerAdapter mAdapter;

    @Bind(R.id.recycler_view_recipes)
    public RecyclerView mRecyclerViewRecipes;

    @Bind(R.id.swipe_refresh_recipes)
    SwipeRefreshLayout mSwipeRefreshLayoutRecipes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBackEnd = App.getBackEndController().getSearchBackEnd(this);
        mCurrentSearchRequest = new SearchRequest();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, layout);
        mRecipes = new ArrayList<>();
        mRecyclerViewRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecipesRecyclerAdapter(mRecipes, this, getActivity());
        mRecyclerViewRecipes.setAdapter(mAdapter);
        mSwipeRefreshLayoutRecipes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBackEnd.Search(getActivity(), mCurrentSearchRequest);
            }
        });
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBackEnd.Search(getActivity(), mCurrentSearchRequest);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivityCallback = (SearchInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement SearchFragmentInterface");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
    }

    @OnClick(R.id.fab_recipes_search)
    public void search(){
        Intent intent = new Intent(getActivity(), SearchParametersActivity.class);
        intent.putExtra(SearchParametersActivity.SEARCH_REQUEST_KEY, mCurrentSearchRequest);
        startActivityForResult(intent, SearchParametersActivity.SEARCH_PARAMETERS_ACTIVITY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == SearchParametersActivity.SEARCH_PARAMETERS_ACTIVITY_CODE){
            mCurrentSearchRequest = (SearchRequest) data.getExtras().getSerializable(SearchParametersActivity.SEARCH_REQUEST_KEY);
            if(!data.getExtras().getBoolean(SearchParametersActivity.IS_CANCELED_KEY))
                mBackEnd.Search(getActivity(), mCurrentSearchRequest);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSearchSuccess(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
        mAdapter.swapItems(mRecipes);
        mSwipeRefreshLayoutRecipes.setRefreshing(false);
    }

    @Override
    public void onSearchError() {
        mSwipeRefreshLayoutRecipes.setRefreshing(false);
        Toast.makeText(getActivity(), R.string.error_search, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNoInternet() {
        mSwipeRefreshLayoutRecipes.setRefreshing(false);
        Toast.makeText(getActivity(), R.string.all_error_no_internet, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInvalidToken() {
        mSwipeRefreshLayoutRecipes.setRefreshing(false);
        Toast.makeText(getActivity(), R.string.all_error_token, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUnknownError() {
        mSwipeRefreshLayoutRecipes.setRefreshing(false);
        Toast.makeText(getActivity(), R.string.all_error_unknown, Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnItemClick(Recipe item) {
        mActivityCallback.openRecipeDetailsActivity(item);
    }
}
