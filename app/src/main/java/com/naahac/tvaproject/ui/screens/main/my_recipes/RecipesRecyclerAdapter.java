package com.naahac.tvaproject.ui.screens.main.my_recipes;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.naahac.tvaproject.R;
import com.naahac.tvaproject.models.Recipe;
import com.naahac.tvaproject.utils.RetrofitUtil;

import java.util.ArrayList;

/**
 * Created by Natanael on 25. 04. 2017.
 */

public class RecipesRecyclerAdapter extends RecyclerView.Adapter {

    public interface ItemInteractionInterface {
        void OnItemClick(Recipe item);
    }

    private ArrayList<Recipe> mItems;
    private ItemInteractionInterface mCallback;
    private Context mContext;

    public RecipesRecyclerAdapter(ArrayList<Recipe> items, ItemInteractionInterface callback, Context context) {
        mContext = context;
        mItems = items;
        mCallback = callback;
    }

    public void swapItems(ArrayList<Recipe> recipes){
        mItems.clear();
        mItems.addAll(recipes);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, null);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Recipe recipe = mItems.get(position);
        ((RecipeViewHolder)holder).bind(recipe);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private Recipe mItem;

        TextView tvRecipeTitle;
        TextView tvRecipePrepTime;
        TextView tvRecipeIngredientsNumber;
        TextView tvRecipeStepsNumber;
        ImageView ivRecipePicture;

        RecipeViewHolder(View itemView) {
            super(itemView);
            tvRecipeTitle = (TextView) itemView.findViewById(R.id.text_view_recipe_title);
            tvRecipeIngredientsNumber = (TextView) itemView.findViewById(R.id.text_view_recipe_ingredients_number);
            tvRecipeStepsNumber = (TextView) itemView.findViewById(R.id.text_view_recipe_steps_number);
            tvRecipePrepTime = (TextView) itemView.findViewById(R.id.text_view_recipe_prep_time);
            ivRecipePicture = (ImageView) itemView.findViewById(R.id.image_view_recipe_image);
        }

        public void bind(Recipe recipe){
            mItem = recipe;
            tvRecipeTitle.setText(mItem.getTitle());
            tvRecipeStepsNumber.setText(String.valueOf(mItem.getPreparationSteps().size()));
            tvRecipeIngredientsNumber.setText(String.valueOf(mItem.getIngredients().size()));
            tvRecipePrepTime.setText(String.valueOf(mItem.getPreparationTime()) + " min");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.OnItemClick(mItem);
                }
            });
            Glide
                    .with(mContext)
                    .load(RetrofitUtil.getBaseUrl() + "picture/" + mItem.getPictureId())
                    .centerCrop()
                    .into(ivRecipePicture);
        }
    }
}
