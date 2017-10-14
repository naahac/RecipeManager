package com.naahac.tvaproject.ui.screens.main.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.controller.backend.request.SearchRequest;
import com.naahac.tvaproject.ui.base.BaseActivity;
import com.naahac.tvaproject.utils.Logger;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Natanael on 13. 04. 2017.
 */

public class SearchParametersActivity extends BaseActivity {

    public static final String SEARCH_REQUEST_KEY = "SEARCH_REQUEST_KEY";
    public static final String IS_CANCELED_KEY = "IS_CANCELED_KEY";
    public static final int SEARCH_PARAMETERS_ACTIVITY_CODE = 106;
    public static final String TAG = SearchParametersActivity.class.getSimpleName();

    private SearchRequest mSearchParameters;

    @Bind(R.id.search_toolbar)
    public Toolbar mToolbar;
    @Bind(R.id.seek_bar_search_ingredients)
    public RangeSeekBar mSeekBarIngredients;
    @Bind(R.id.seek_bar_search_preparation_steps)
    public RangeSeekBar mSeekBarPreparationSteps;
    @Bind(R.id.seek_bar_search_preparation_time)
    public RangeSeekBar mSeekBarPreparationTime;
    @Bind(R.id.edit_text_recipe_name_search)
    public TextInputEditText mTilRecipeName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        ButterKnife.bind(this);
        mSearchParameters = (SearchRequest) getIntent().getSerializableExtra(SEARCH_REQUEST_KEY);
        if(mSearchParameters == null) {
            mSearchParameters = new SearchRequest();
            setDefaults();
        }
        else
            setUI();
        mTilRecipeName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    finishWithResult(false);
                    return true;
                }
                return false;
            }
        });
        setToolbar();
    }

    private void setToolbar(){
        mToolbar.setTitle("Search");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.lightGrey));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithResult(true);
            }
        });
    }

    private void setDefaults(){
        mSeekBarIngredients.setSelectedMinValue(5);
        mSeekBarIngredients.setSelectedMaxValue(20);

        mSeekBarPreparationSteps.setSelectedMinValue(5);
        mSeekBarPreparationSteps.setSelectedMaxValue(20);

        mSeekBarPreparationTime.setSelectedMaxValue(5);
        mSeekBarPreparationTime.setSelectedMaxValue(100);
    }

    private void setUI(){
        mTilRecipeName.setText(mSearchParameters.getRecipeName());

        mSeekBarIngredients.setSelectedMinValue(mSearchParameters.getMinIngredients());
        mSeekBarIngredients.setSelectedMaxValue(mSearchParameters.getMaxIngredients());

        mSeekBarPreparationSteps.setSelectedMinValue(mSearchParameters.getMinPreparationSteps());
        mSeekBarPreparationSteps.setSelectedMaxValue(mSearchParameters.getMaxPreparationSteps());

        mSeekBarPreparationTime.setSelectedMaxValue(mSearchParameters.getMaxPreparationTime());
        mSeekBarPreparationTime.setSelectedMinValue(mSearchParameters.getMinPreparationTime());
    }

    @OnClick(R.id.button_search_recipes)
    public void search(){
        finishWithResult(false);
    }

    private void finishWithResult(boolean isCanceled){
        Logger.print(TAG, "finish clicked. Min seek bar: " + mSeekBarPreparationSteps.getSelectedMinValue().toString() + " Max: " + mSeekBarPreparationSteps.getSelectedMaxValue().toString());
        mSearchParameters.setMinIngredients((int)mSeekBarIngredients.getSelectedMinValue());
        mSearchParameters.setMaxIngredients((int)mSeekBarIngredients.getSelectedMaxValue());
        mSearchParameters.setMinPreparationSteps((int)mSeekBarPreparationSteps.getSelectedMinValue());
        mSearchParameters.setMaxPreparationSteps((int)mSeekBarPreparationSteps.getSelectedMaxValue());
        mSearchParameters.setMinPreparationTime((int)mSeekBarPreparationTime.getSelectedMinValue());
        mSearchParameters.setMaxPreparationTime((int)mSeekBarPreparationTime.getSelectedMaxValue());
        mSearchParameters.setRecipeName(mTilRecipeName.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(SEARCH_REQUEST_KEY, mSearchParameters);
        intent.putExtra(IS_CANCELED_KEY, isCanceled);
        setResult(SEARCH_PARAMETERS_ACTIVITY_CODE, intent);
        finish();
    }
}
