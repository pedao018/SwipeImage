package com.hainguyen.myapplication;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.hainguyen.myapplication.adapter.ViewPagerAdapter;
import com.hainguyen.myapplication.ui.ViewPagerLayout;

import java.util.List;

public class ListImageDialog extends Dialog {
    private ViewPagerLayout mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    public ListImageDialog(@NonNull Context context, List<WebItem> dataSet, int currentPosition) {
        super(context);
        setTitle("Hinh anh");
        setCancelable(true);

            /*int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90); //<-- int width=400;
            int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.90);//<-- int height =300;
            View view1 = LayoutInflater.from(context).inflate(R.layout.image_dialog, null);
            dialog.setContentView(view1, new ViewGroup.LayoutParams(width, height));*/
        setContentView(R.layout.image_dialog);

        mViewPager = (ViewPagerLayout) findViewById(R.id.viewPagerMain);
        mViewPagerAdapter = new ViewPagerAdapter(context, dataSet);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(currentPosition);
    }


}
