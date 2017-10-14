package com.naahac.tvaproject.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.models.Ingredient;
import com.naahac.tvaproject.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natanael on 12. 06. 2017.
 */

public class IngredientsAutocompleteAdapter extends BaseAdapter implements Filterable {

    private static final String  TAG = IngredientsAutocompleteAdapter.class.getSimpleName();

    public interface OnAction{
        void Search(String searchString);
        void OnItemClick(Ingredient ingredient);
    }

    private static final int MAX_RESULTS = 10;
    private boolean gotResults = false;
    private Context mContext;
    private List<Ingredient> resultList = new ArrayList<Ingredient>();
    private OnAction mCallback;

    public IngredientsAutocompleteAdapter(Context context, OnAction callback) {
        this.mCallback = callback;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Ingredient getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ingredient_autocomplete_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.text_view_ingredient_name)).setText(getItem(position).getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.print(TAG, "item clicked");
                mCallback.OnItemClick(getItem(position));
            }
        });
        return convertView;
    }

    public void OnSearchResult(ArrayList<Ingredient> ingredients){
        resultList = ingredients;
        gotResults = true;
    }

    public void OnSearchError(){
        gotResults = true;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    gotResults = false;
                    mCallback.Search(constraint.toString());
                    while (!gotResults){

                    }
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<Ingredient>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
