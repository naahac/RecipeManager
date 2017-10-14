package com.naahac.tvaproject.ui.screens.recipe_details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.naahac.tvaproject.R;
import com.naahac.tvaproject.app.App;
import com.naahac.tvaproject.controller.backend.DataStorage;
import com.naahac.tvaproject.controller.backend.RecipeDetailsBackEnd;
import com.naahac.tvaproject.controller.frontend.RecipeDetailsFrontEnd;
import com.naahac.tvaproject.models.Ingredient;
import com.naahac.tvaproject.models.Nutrient;
import com.naahac.tvaproject.models.Picture;
import com.naahac.tvaproject.models.PreparationStep;
import com.naahac.tvaproject.models.Recipe;
import com.naahac.tvaproject.ui.base.BaseActivity;
import com.naahac.tvaproject.ui.custom_views.ResizableRecyclerView;
import com.naahac.tvaproject.ui.dialog.AddNewIngredientDialog;
import com.naahac.tvaproject.ui.dialog.AddNewPreparationStepDialog;
import com.naahac.tvaproject.ui.dialog.NutritionsDialog;
import com.naahac.tvaproject.utils.AppBarStateChangeListener;
import com.naahac.tvaproject.utils.Logger;
import com.naahac.tvaproject.utils.RetrofitUtil;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Natanael on 13. 04. 2017.
 */

public class RecipeDetailsActivity extends BaseActivity implements IngredientsRecyclerAdapter.ItemInteractionInterface, PreparationStepsRecyclerAdapter.ItemInteractionInterface, AddNewPreparationStepDialog.OnUserAction, AddNewIngredientDialog.OnUserAction, RecipeDetailsFrontEnd {

    public static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    private static final int PICK_IMAGE_REQUEST = 1;
    public static final String RECIPE_KEY = "RECIPE_KEY";
    public static final String IS_EDITABLE_KEY = "IS_EDITABLE_KEY";

    private boolean isEditEnabled;
    private Recipe mRecipe;
    private boolean isEditable;
    private RecipeDetailsBackEnd mRecipeDetailsBackEnd;
    private IngredientsRecyclerAdapter mIngredientsAdapter;
    private PreparationStepsRecyclerAdapter mCookingStepsAdapter;
    private Bitmap mCurrentlyLoadedPicture;

    // Actionbar
    @Bind(R.id.toolbar)
    public Toolbar mToolbar;
    @Bind(R.id.fab_recipe_details_action)
    public FloatingActionButton mFabAction;
    @Bind(R.id.image_view_recipe_details)
    ImageView mRecipeImage;
    @Bind(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.recycler_view_ingredients)
    public ResizableRecyclerView mRecyclerViewIngredients;
    @Bind(R.id.recycler_view_cooking_steps)
    public RecyclerView mRecyclerViewCookingSteps;
    @Bind(R.id.appBar)
    public AppBarLayout mAppBar;
    @Bind(R.id.image_view_recipe_add)
    public ImageView ivRecipeAdd;
    @Bind(R.id.edit_line)
    public View mEditLine;
    @Bind(R.id.description_line)
    public View mDescriptionLine;
    @Bind(R.id.edit_text_recipe_name)
    public TextInputEditText mEtRecipeName;
    @Bind(R.id.edit_text_recipe_description)
    public TextInputEditText mEtRecipeDescription;
    @Bind(R.id.text_view_recipe_description)
    public TextView mTvDescription;
    @Bind(R.id.seek_bar_search_preparation_time)
    public RangeSeekBar mSeekBarPreparationTime;
    @Bind(R.id.text_view_recipe_prep_time)
    public TextView mTvPrepTime;

    private AddNewIngredientDialog mDialog;

    private DataStorage mStorage;

    private boolean checkInputs(){
        boolean isValid = true;
        String description = mEtRecipeDescription.getText().toString();
        String name = mEtRecipeName.getText().toString();

        if(description.isEmpty()){
            isValid = false;
            mEtRecipeDescription.setError(getString(R.string.all_error_empty));
        }
        if(name.isEmpty()){
            isValid = false;
            mEtRecipeName.setError(getString(R.string.all_error_empty));
        }
        if(mCurrentlyLoadedPicture == null && mRecipe.isNew()){
            isValid = false;
            Toast.makeText(this, "Please pick a picture!", Toast.LENGTH_LONG).show();
        }
        return isValid;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        mStorage = App.getBackEndController().getDataStorage();
        mRecipe = (Recipe) getIntent().getExtras().getSerializable(RECIPE_KEY);
        int storageUserId = mStorage.getIntFromPrefs(this, DataStorage.PREF_KEY_USER_ID);
        isEditEnabled = getIntent().getExtras().getBoolean(IS_EDITABLE_KEY) && (mRecipe == null || storageUserId == mRecipe.getUserId());
        mRecipeDetailsBackEnd = App.getBackEndController().getRecipeDetailsBackEnd(this);
        if(mRecipe == null){
            isEditable = true;
            mRecipe = new Recipe(true);
        }
        setActionBar();
        setUI();
        switchEditMode();
    }

    private void setActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeDetailsActivity.this.finish();
            }
        });

        mAppBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else if (state == State.EXPANDED) {
                    mToolbar.setBackground(getResources().getDrawable(R.drawable.black_top_to_bottom_gradient));
                } else if (state == State.IDLE) {
                    mToolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
                Logger.print("STATE", state.name());
            }
        });
    }

    private void switchEditMode() {
        if (isEditable) {
            ivRecipeAdd.setVisibility(View.VISIBLE);
            mEditLine.setVisibility(View.VISIBLE);
            mDescriptionLine.setVisibility(View.GONE);
            if(!mRecipe.isNew()){
                mEtRecipeName.setText(mRecipe.getTitle());
                mEtRecipeDescription.setText(mRecipe.getDescription());
                mSeekBarPreparationTime.setSelectedMaxValue(mRecipe.getPreparationTime());
            }
        } else {
            ivRecipeAdd.setVisibility(View.INVISIBLE);
            mEditLine.setVisibility(View.GONE);
            mDescriptionLine.setVisibility(View.VISIBLE);
        }
        mIngredientsAdapter.setIsEditable(isEditable);
        mCookingStepsAdapter.setIsEditable(isEditable);
    }

    private void setUI() {
        mEtRecipeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCollapsingToolbarLayout.setTitle(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRecyclerViewIngredients.setLayoutManager(new LinearLayoutManager(this));
        mFabAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO share
            }
        });
        List<PreparationStep> cookingSteps = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        if (!mRecipe.isNew()) {
            mCollapsingToolbarLayout.setTitle(mRecipe.getTitle());
            Glide
                    .with(this)
                    .load(RetrofitUtil.getBaseUrl() + "picture/" + mRecipe.getPictureId())
                    .asBitmap()
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            mCurrentlyLoadedPicture = resource;
                            mRecipeImage.setImageBitmap(resource);
                        }
                    });
            ingredients = mRecipe.getIngredients();
            cookingSteps = mRecipe.getPreparationSteps();
            mTvDescription.setText(mRecipe.getDescription());
            mTvPrepTime.setText(String.valueOf(mRecipe.getPreparationTime()) + " min");
        }
        mIngredientsAdapter = new IngredientsRecyclerAdapter(ingredients, this, this, this.isEditable);
        mRecyclerViewIngredients.setAdapter(mIngredientsAdapter);
        mRecyclerViewCookingSteps.setLayoutManager(new LinearLayoutManager(this));
        mCookingStepsAdapter = new PreparationStepsRecyclerAdapter(cookingSteps, this, this, this.isEditable);
        mRecyclerViewCookingSteps.setAdapter(mCookingStepsAdapter);
    }


    @OnClick(R.id.image_view_recipe_add)
    public void selectImageFromGallery() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Glide
                    .with(this)
                    .load(uri)
                    .asBitmap()
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mCurrentlyLoadedPicture = resource;
                            mRecipeImage.setImageBitmap(resource);
                        }
                    });
        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        Logger.print(TAG, encodedImage);
        return encodedImage;
    }

    private void saveData(){
        if(!checkInputs())
            return;
        Picture picture = new Picture();
        if(mCurrentlyLoadedPicture == null){
            picture.setPictureId(mRecipe.getPictureId());
        }else {
            picture.setBase64Data("data:image/jpeg;base64," + convertBitmapToBase64(mCurrentlyLoadedPicture));
            picture.setBase64Data("data:image/jpeg;base64," + convertBitmapToBase64(mCurrentlyLoadedPicture));
        }
        String name = mEtRecipeName.getText().toString().equals("") ? mRecipe.getTitle() : mEtRecipeName.getText().toString();
        String description = mEtRecipeDescription.getText().toString().equals("") ? mRecipe.getDescription() : mEtRecipeDescription.getText().toString();
        Long recipeId = mRecipe.isNew() ? -1 : mRecipe.getRecipeId();
        int prepTime = (int)mSeekBarPreparationTime.getSelectedMaxValue();
        Logger.print(TAG, String.valueOf(prepTime));

        Recipe recipe = new Recipe(recipeId, picture, name, description, true, mIngredientsAdapter.getItems(), mCookingStepsAdapter.getItems(), prepTime);

        mRecipeDetailsBackEnd.CreateRecipe(this, recipe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_details_menu, menu);
        if(isEditEnabled){
            if (this.isEditable)
                menu.findItem(R.id.action_edit).setIcon(getResources().getDrawable(R.drawable.ic_done_white_24dp));
        }else{
            menu.removeItem(R.id.action_edit);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                isEditable = !isEditable;
                if(isEditable){
                    item.setIcon(getResources().getDrawable(R.drawable.ic_done_white_24dp));
                    switchEditMode();
                }
                else{
                    if(!checkInputs()){
                        isEditable = !isEditable;//change back to edit mode
                        return true;
                    }
                    item.setIcon(getResources().getDrawable(R.drawable.ic_mode_edit_white_24dp));
                    switchEditMode();
                    saveData();
                }
                return true;
            case R.id.action_refresh:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void OnItemClick(Ingredient item) {
        if(item.getUsdaNdbId() == null || item.getUsdaNdbId().equals("")){
            Logger.print(TAG, "no usdandb ID");
            return;
        }
        mRecipeDetailsBackEnd.GetIngredientNutrition(this, item.getUsdaNdbId());
    }

    @Override
    public void OnAddIngredientClick() {
        mDialog = AddNewIngredientDialog.newInstance(this);
        mDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void OnItemClick(PreparationStep item) {

    }

    @Override
    public void OnAddNewCookingStepClick() {
        AddNewPreparationStepDialog.newInstance(this).show(getFragmentManager(), TAG);
    }

    @Override
    public void OnSave(PreparationStep preparationStep) {
        Logger.print(TAG, preparationStep.getDescription());
        mCookingStepsAdapter.addStep(preparationStep);
    }

    @Override
    public void OnSave(Ingredient ingredient) {
        Logger.print(TAG, ingredient.getName());
        mIngredientsAdapter.addIngredient(ingredient);
    }

    @Override
    public void onNoInternet() {
        Toast.makeText(this, "No internet!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInvalidToken() {
        Toast.makeText(this, "Invalid token! Please restart application.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUnknownError() {
        Toast.makeText(this, "Unknown error!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddRecipeSuccess() {
        Toast.makeText(this, "Recipe succesfully saved!", Toast.LENGTH_LONG).show();
        if(mRecipe.isNew())
            this.finish();
    }

    @Override
    public void onAddRecipeFailure() {
        Toast.makeText(this, "Recipe was not saved!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuggestionResult(ArrayList<Ingredient> ingredients) {
//        for (Ingredient i :
//                ingredients) {
//            Logger.print(TAG, "Suggestion: " + i.getName() + ";" + i.getUsdaNdbId());
//        }
        mDialog.SetSuggestions(ingredients);
    }

    @Override
    public void onGetNutritionResult(ArrayList<Nutrient> nutrients) {
        String output = "";
        for (Nutrient n :
                nutrients) {
            output += n.getName() + ": " + n.getValue() + n.getUnit() + "\n";
        }
        NutritionsDialog.newInstance(output).show(getFragmentManager(), TAG);
        Logger.print(TAG, nutrients.get(0).getName());
    }

    @Override
    public void onGetNutritionError() {
        mDialog.SuggestionError();
    }

    @Override
    public void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void GetIngredientSuggestions(String searchString) {
        mRecipeDetailsBackEnd.GetIngredientSuggestions(this,searchString);
    }
}
