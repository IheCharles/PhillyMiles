package com.example.phillymiles;

import android.content.Context;

import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by Dell on 2018/01/29.
 */
public class HPLinearLayoutManager extends LinearLayoutManager {

    public HPLinearLayoutManager(Context context) {
        super(context);
    }

    public HPLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public HPLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Magic here
     */
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}