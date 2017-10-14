package com.naahac.tvaproject.utils;

import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Natanael on 16. 05. 2017.
 */
public class OnViewGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private View view;
    private int maxHeight;
    public OnViewGlobalLayoutListener(View view, int maxHeight) {
        this.view = view;
        this.maxHeight = maxHeight;
    }

    @Override
    public void onGlobalLayout() {
        if (view.getLayoutParams().height > maxHeight)
            view.getLayoutParams().height = maxHeight;
    }
}
