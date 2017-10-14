package com.naahac.tvaproject.ui.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.naahac.tvaproject.R;

/**
 * Created by Natanael on 16. 05. 2017.
 */

public class ResizableRecyclerView extends RecyclerView {

    private int maxHeight;
    public ResizableRecyclerView(Context context) {
        super(context);
        getStyledAttributes(context, null);
    }

    public ResizableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getStyledAttributes(context, attrs);
    }

    public ResizableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getStyledAttributes(context, attrs);
    }

    private void getStyledAttributes(Context context, @Nullable AttributeSet attrs){
        TypedArray array = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ResizableRecyclerView,
                0, 0);
        try {
            maxHeight = (int)array.getDimension(R.styleable.ResizableRecyclerView_maxHeight, 1);
        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (maxHeight > 0){
            int hSize = MeasureSpec.getSize(heightSpec);
            int hMode = MeasureSpec.getMode(heightSpec);

            switch (hMode){
                case MeasureSpec.AT_MOST:
                    heightSpec = MeasureSpec.makeMeasureSpec(Math.min(hSize, maxHeight), MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.UNSPECIFIED:
                    heightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.EXACTLY:
                    heightSpec = MeasureSpec.makeMeasureSpec(Math.min(hSize, maxHeight), MeasureSpec.EXACTLY);
                    break;
            }
        }
        super.onMeasure(widthSpec, heightSpec);
    }
}
