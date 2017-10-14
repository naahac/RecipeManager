package com.naahac.tvaproject.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.models.PreparationStep;
import com.naahac.tvaproject.ui.base.BasePopupDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewPreparationStepDialog extends BasePopupDialogFragment {

    public interface OnUserAction{
        void OnSave(PreparationStep preparationStep);
        void hideSoftInput();
    }

    @Bind(R.id.edit_text_cooking_step_description)
    TextInputEditText mEtDescription;

    private OnUserAction receiver;

    public static AddNewPreparationStepDialog newInstance(OnUserAction receiver) {
        AddNewPreparationStepDialog f = new AddNewPreparationStepDialog();
        f.receiver = receiver;
        return f;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        receiver.hideSoftInput();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.save_button)
    public void save(){
        receiver.OnSave(new PreparationStep(-1l, mEtDescription.getText().toString(), 0L));
        this.dismiss();
    }

    @OnClick(R.id.dismiss_button)
    public void dismissClick(){
        this.dismiss();
    }

    @OnClick(R.id.save_another_button)
    public void addAnotherClick(){
        receiver.OnSave(new PreparationStep(-1l, mEtDescription.getText().toString(), 0L));
        mEtDescription.setText("");
        Toast.makeText(getActivity(), "Preparation step added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_new_cooking_step, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Add new Cooking step");
    }
}
