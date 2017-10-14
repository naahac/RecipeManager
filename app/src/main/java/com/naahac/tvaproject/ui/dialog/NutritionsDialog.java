package com.naahac.tvaproject.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.ui.base.BasePopupDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NutritionsDialog extends BasePopupDialogFragment {

    @Bind(R.id.text_view_nutritions)
    public TextView mTvNutritions;

    private String nutritions = "";

    public static NutritionsDialog newInstance(String nutritions) {
        NutritionsDialog f = new NutritionsDialog();
        f.nutritions = nutritions;
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.dismiss_button)
    public void dismissClick(){
        this.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_nutritions, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Add new ingredient");
        setUI();
    }

    private void setUI(){
        mTvNutritions.setText(nutritions);
    }
}
