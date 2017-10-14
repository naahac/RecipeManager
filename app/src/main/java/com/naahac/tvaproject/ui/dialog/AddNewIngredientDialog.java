package com.naahac.tvaproject.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.models.Ingredient;
import com.naahac.tvaproject.ui.base.BasePopupDialogFragment;
import com.naahac.tvaproject.ui.widget.DelayAutoCompleteTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewIngredientDialog extends BasePopupDialogFragment implements IngredientsAutocompleteAdapter.OnAction {

    public interface OnUserAction{
        void OnSave(Ingredient ingredient);
        void hideSoftInput();
        void GetIngredientSuggestions(String searchString);
    }

    @Bind(R.id.spinner_ingredient_measure)
    Spinner mSpinnerMeasure;
    @Bind(R.id.edit_text_ingredient_amount)
    TextInputEditText mEtAmount;
    @Bind(R.id.edit_text_ingredient_name)
    DelayAutoCompleteTextView mEtName;
    @Bind(R.id.pb_loading_indicator)
    android.widget.ProgressBar mProgressBar;
    private IngredientsAutocompleteAdapter mAdapter;
    private String usdaid;


    private OnUserAction receiver;
    private String selectedItemValue = "g";

    public static AddNewIngredientDialog newInstance(OnUserAction receiver) {
        AddNewIngredientDialog f = new AddNewIngredientDialog();
        f.receiver = receiver;
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void SetSuggestions(ArrayList<Ingredient> ingredients){
        mAdapter.OnSearchResult(ingredients);
    }

    public void SuggestionError(){
        mAdapter.OnSearchError();
    }

    @OnClick(R.id.save_button)
    public void save(){
        receiver.OnSave(new Ingredient(-1l, mEtName.getText().toString(), mEtAmount.getText().toString() + " " + selectedItemValue, 0L, usdaid));
        this.dismiss();
    }

    @OnClick(R.id.save_another_button)
    public void addAnotherClick(){
        receiver.OnSave(new Ingredient(-1l, mEtName.getText().toString(), mEtAmount.getText().toString() + " " + selectedItemValue, 0L, usdaid));
        mEtName.setText("");
        mEtAmount.setText("");
        mEtName.requestFocus();
        Toast.makeText(getActivity(), "Ingredient added!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.dismiss_button)
    public void dismissClick(){
        this.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        receiver.hideSoftInput();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_new_ingredient, container, false);
        ButterKnife.bind(this, view);
        mEtName.setThreshold(3);
        mAdapter = new IngredientsAutocompleteAdapter(getActivity(), this);
        mEtName.setAdapter(mAdapter);
        mEtName.setLoadingIndicator(mProgressBar);
        mEtName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Ingredient ingredient = (Ingredient) adapterView.getItemAtPosition(position);
                mEtName.setText(ingredient.getName());
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Add new ingredient");
        setUI();
    }

    private void setUI(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.measures_sppinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMeasure.setAdapter(adapter);
        mSpinnerMeasure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemValue = getResources().getStringArray(R.array.measures_sppinner_item_values)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void Search(String searchString) {
        receiver.GetIngredientSuggestions(searchString);
    }

    @Override
    public void OnItemClick(Ingredient ingredient) {
        usdaid = ingredient.getUsdaNdbId();
        mEtName.dismissDropDown();
        mEtName.setText(ingredient.getName());
    }
}
