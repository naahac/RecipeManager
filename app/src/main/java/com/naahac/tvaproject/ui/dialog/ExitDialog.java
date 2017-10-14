package com.naahac.tvaproject.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.ui.base.BasePopupDialogFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExitDialog extends BasePopupDialogFragment {

    public interface OnUserAction{
        void OnExit();
    }

    private OnUserAction receiver;

    public static ExitDialog newInstance(OnUserAction receiver) {
        ExitDialog f = new ExitDialog();
        f.receiver = receiver;
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.save_button)
    public void exit(){
        receiver.OnExit();
    }

    @OnClick(R.id.dismiss_button)
    public void dismissClick(){
        this.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_exit, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
