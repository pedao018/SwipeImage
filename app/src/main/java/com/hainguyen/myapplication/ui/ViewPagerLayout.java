package com.hainguyen.myapplication.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.hainguyen.myapplication.R;

public class ViewPagerLayout extends ViewPager {
    //private SwipeDetectModel swipeDetectModel;

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

        //Vì PhotoView zoom image đã có detect lướt tay nên ko cần làm code trong đây
        /*swipeDetectModel = new SwipeDetectModel(context, new SwipeDetectModel.SwipeListener() {
            @Override
            public void onSwipeToRight() {
                Log.e("hahaha", "SwipeToRight first");
            }

            @Override
            public void onSwipeToLeft() {
                Log.e("hahaha", "SwipeToLeft first");
            }

            @Override
            public void onSwipeToTop() {
                Log.e("hahaha", "SwipeToTop first");
            }

            @Override
            public void onSwipeToBottom() {
                Log.e("hahaha", "SwipeToBottom frist");
            }
        });*/
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent ev) {
        swipeDetectModel.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }*/

    //Mục đích để fix bug crash do zoom out image
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //Mục đích để fix bug crash do zoom out image
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
