package com.naahac.tvaproject.ui.screens.recipe_details;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.models.Ingredient;
import com.naahac.tvaproject.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natanael on 25. 04. 2017.
 */

public class IngredientsRecyclerAdapter extends RecyclerView.Adapter {

    private static final String TAG = IngredientsRecyclerAdapter.class.getSimpleName();

    public interface ItemInteractionInterface{
        void OnItemClick(Ingredient item);
        void OnAddIngredientClick();
    }

    private List<Ingredient> mItems;
    private ItemInteractionInterface mCallback;
    private Context mContext;
    private boolean isEditable;


    public IngredientsRecyclerAdapter(List<Ingredient> items, ItemInteractionInterface callback, Context context, boolean isEditable) {
        this.mCallback = callback;
        this.mContext = context;
        swapItems(items, isEditable);
    }

    public void setIsEditable(boolean isEditable){
        if(!this.isEditable && isEditable){
            Ingredient dummyLastItem = new Ingredient();//for add ingredient item
            mItems.add(dummyLastItem);
        }else if(this.isEditable && !isEditable){
            mItems.remove(mItems.size() - 1);
        }
        this.isEditable = isEditable;
        notifyDataSetChanged();
    }

    public void swapItems(List<Ingredient> items, boolean isEditable){
        if(mItems == null)
            mItems = new ArrayList<>();
        else
            mItems.clear();
        mItems.addAll(items);
        setIsEditable(isEditable);
    }

    private void removeItem(int index){
        mItems.remove(index);
        notifyItemRemoved(index);
    }

    public void addIngredient(Ingredient ingredient){
        int index = mItems.size() - 1;
        mItems.add(index, ingredient);
        notifyItemInserted(index);
    }

    public List<Ingredient> getItems(){
        if(!isEditable)
            return mItems;
        //remove last item if in edit mode
        List<Ingredient> tmpList = mItems;
        tmpList.remove(tmpList.size() -1);
        return tmpList;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mItems.size() - 1 && isEditable ? AddIngredientViewHolder.ADD_INGREDIENT_VIEW_TYPE : IngredientViewHolder.INGREDIENT_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case AddIngredientViewHolder.ADD_INGREDIENT_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_add_item, null);
                viewHolder = new AddIngredientViewHolder(view);
                break;
            case IngredientViewHolder.INGREDIENT_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, null);
                viewHolder = new IngredientViewHolder(view);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, null);
                viewHolder = new IngredientViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof IngredientViewHolder){
            Ingredient item = mItems.get(position);
            ((IngredientViewHolder)holder).bind(item);
        }else if (holder instanceof AddIngredientViewHolder){
            ((AddIngredientViewHolder)holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private class IngredientViewHolder extends RecyclerView.ViewHolder {
        final static int INGREDIENT_VIEW_TYPE = 1;

        private Ingredient mItem;

        TextView tvIngredientName;
        TextView tvIngredientAmount;
        ImageView ivRemove;

        IngredientViewHolder(View itemView) {
            super(itemView);
            tvIngredientName = (TextView) itemView.findViewById(R.id.text_view_ingredient_name);
            tvIngredientAmount = (TextView) itemView.findViewById(R.id.text_view_ingredient_amount);
            ivRemove = (ImageView) itemView.findViewById(R.id.text_view_ingredient_remove);
        }

        public void bind(Ingredient item){
            mItem = item;
            tvIngredientName.setText(mItem.getName());
            tvIngredientAmount.setText(mItem.getAmount());

            if(getAdapterPosition() % 2 == 1)
                itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            if(isEditable){
                ivRemove.setVisibility(View.VISIBLE);
            }else {
                ivRemove.setVisibility(View.INVISIBLE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.print(TAG, "ingredient clicked");
                    mCallback.OnItemClick(mItem);
                }
            });
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(getAdapterPosition());
                }
            });
        }
    }

    private class AddIngredientViewHolder extends RecyclerView.ViewHolder {
        final static int ADD_INGREDIENT_VIEW_TYPE = 2;

        AddIngredientViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.OnAddIngredientClick();
                }
            });
        }
    }
}
