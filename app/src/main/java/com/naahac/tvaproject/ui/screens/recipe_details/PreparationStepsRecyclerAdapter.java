package com.naahac.tvaproject.ui.screens.recipe_details;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.models.PreparationStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natanael on 25. 04. 2017.
 */

public class PreparationStepsRecyclerAdapter extends RecyclerView.Adapter {

    public interface ItemInteractionInterface{
        void OnItemClick(PreparationStep item);
        void OnAddNewCookingStepClick();
    }

    private List<PreparationStep> mItems;
    private ItemInteractionInterface mCallback;
    private Context mContext;
    private boolean isEditable;

    public PreparationStepsRecyclerAdapter(List<PreparationStep> items, ItemInteractionInterface callback, Context context, boolean isEditable) {
        mCallback = callback;
        mContext = context;
        swapItems(items, isEditable);
    }

    public void setIsEditable(boolean isEditable){
        if(!this.isEditable && isEditable){
            PreparationStep dummyLastItem = new PreparationStep();//for ADD item
            mItems.add(dummyLastItem);
        }else if(this.isEditable && !isEditable){
            mItems.remove(mItems.size() - 1);
        }
        this.isEditable = isEditable;
        notifyDataSetChanged();
    }

    public void swapItems(List<PreparationStep> items, boolean isEditable){
        if(mItems == null)
            mItems = new ArrayList<>();
        else
            mItems.clear();
        mItems.clear();
        mItems.addAll(items);
        setIsEditable(isEditable);
    }

    private void removeItem(int index) {
        mItems.remove(index);
        notifyItemRemoved(index);
    }

    public void addStep(PreparationStep cookingStep){
        int index = mItems.size() - 1;
        mItems.add(index, cookingStep);
        notifyItemInserted(index);
    }

    public List<PreparationStep> getItems(){
        if(!isEditable)
            return mItems;
        //remove last item if in edit mode
        List<PreparationStep> tmpList = mItems;
        tmpList.remove(tmpList.size() -1);
        return tmpList;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mItems.size() - 1 && isEditable ? AddCookingStepViewHolder.ADD_COOKING_STEP_VIEW_TYPE : CookingStepViewHolder.COOKING_STEP_VIEW_TYPE;//last item is ADD_COOKING_STEP
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case AddCookingStepViewHolder.ADD_COOKING_STEP_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cooking_step_add_item, null);
                viewHolder = new AddCookingStepViewHolder(view);
                break;
            case CookingStepViewHolder.COOKING_STEP_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cooking_step_item, null);
                viewHolder = new CookingStepViewHolder(view);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cooking_step_item, null);
                viewHolder = new CookingStepViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof CookingStepViewHolder){
            PreparationStep item = mItems.get(position);
            ((CookingStepViewHolder)holder).bind(item);
        }else if (holder instanceof AddCookingStepViewHolder){
            ((AddCookingStepViewHolder)holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private class CookingStepViewHolder extends RecyclerView.ViewHolder {
        final static int COOKING_STEP_VIEW_TYPE = 1;

        private PreparationStep mItem;

        TextView tvCookingStepDescription;
        ImageView ivRemove;
        TextView tvCookingStepNumber;

        CookingStepViewHolder(View itemView) {
            super(itemView);
            tvCookingStepDescription = (TextView) itemView.findViewById(R.id.text_view_cooking_step_name);
            ivRemove = (ImageView) itemView.findViewById(R.id.text_view_cooking_step_remove);
            tvCookingStepNumber = (TextView) itemView.findViewById(R.id.text_view_cooking_step_number);
        }

        public void bind(PreparationStep item){
            mItem = item;
            tvCookingStepDescription.setText(mItem.getDescription());
            tvCookingStepNumber.setText(String.valueOf(getAdapterPosition() + 1));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.OnItemClick(mItem);
                }
            });
            if(getAdapterPosition() % 2 == 1)
                itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            if(isEditable){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.OnItemClick(mItem);
                    }
                });
                ivRemove.setVisibility(View.VISIBLE);
            }else {
                ivRemove.setVisibility(View.INVISIBLE);
            }
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(getAdapterPosition());
                }
            });
        }
    }

    private class AddCookingStepViewHolder extends RecyclerView.ViewHolder {
        final static int ADD_COOKING_STEP_VIEW_TYPE = 2;

        AddCookingStepViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.OnAddNewCookingStepClick();
                }
            });
        }
    }
}
