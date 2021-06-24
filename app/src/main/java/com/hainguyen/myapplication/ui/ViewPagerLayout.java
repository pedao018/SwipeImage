package com.hainguyen.myapplication.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.hainguyen.myapplication.R;
import com.hainguyen.myapplication.ui.SwipeDetectModel;

public class ViewPagerLayout extends ViewPager {
    private SwipeDetectModel swipeDetectModel;

    public ViewPagerLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ViewPagerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(@NonNull Context context) {
        inflate(context, R.layout.view_pager_layout, this);

        swipeDetectModel = new SwipeDetectModel(context, new SwipeDetectModel.SwipeListener() {
            @Override
            public void onSwipeToRight() {
                Log.e("hahaha", "SwipeToRight");
            }

            @Override
            public void onSwipeToLeft() {
                Log.e("hahaha", "SwipeToLeft");
            }

            @Override
            public void onSwipeToTop() {
                Log.e("hahaha", "SwipeToTop");
            }

            @Override
            public void onSwipeToBottom() {
                Log.e("hahaha", "SwipeToBottom");
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        swipeDetectModel.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }
}
