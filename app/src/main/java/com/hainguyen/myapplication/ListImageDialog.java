package com.hainguyen.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.hainguyen.myapplication.adapter.ReviewImageAdapter;
import com.hainguyen.myapplication.adapter.ViewPagerAdapter;
import com.hainguyen.myapplication.model.ImageItem;
import com.hainguyen.myapplication.ui.ViewPagerLayout;

import java.util.ArrayList;
import java.util.List;

public class ListImageDialog extends Dialog {
    private ViewPagerLayout mViewImageViewPager;
    private ViewPagerAdapter mViewImageViewPagerAdapter;
    public RecyclerView mReviewImageListRecycleView;
    private ReviewImageAdapter mReviewImageListAdapter;
    private List<ImageItem> mReviewImageList;
    private int prevHighlightItem; //Dùng để xác định item được chọn trước đó trong mReviewImageList

    private Context context;

    public ListImageDialog(@NonNull Context context, List<ImageItem> dataSet, int currentPosition) {
        super(context, R.style.ThemeOverlay_MaterialComponents);
        setTitle("Hinh anh");
        setCancelable(true);
        setContentView(R.layout.view_image_dialog);

        this.context = context;
        for (ImageItem item : dataSet) {
            item.isSelected = false;
        }
        dataSet.get(currentPosition).isSelected = true;
        this.mReviewImageList = dataSet;
        prevHighlightItem = currentPosition;

        mViewImageViewPager = (ViewPagerLayout) findViewById(R.id.view_image_dialog_viewpager);
        mViewImageViewPagerAdapter = new ViewPagerAdapter(this.context, this.mReviewImageList);
        mViewImageViewPager.setAdapter(mViewImageViewPagerAdapter);
        mViewImageViewPager.setCurrentItem(currentPosition);
        mViewImageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setHighlightSelectedItemImageList(position);
                mReviewImageListRecycleView.smoothScrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mReviewImageListRecycleView = (RecyclerView) findViewById(R.id.view_image_dialog_review_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false);
        mReviewImageListRecycleView.setLayoutManager(layoutManager);
        mReviewImageListRecycleView.setItemAnimator(new DefaultItemAnimator());
        mReviewImageListRecycleView.setFocusable(false);
        mReviewImageListRecycleView.setHasFixedSize(true);//true Để smooth scroll được full view của item
        //((LinearLayoutManager)layoutManager).setStackFromEnd(true); //true để smooth scroll được lúc mới mở màn hình
        mReviewImageListAdapter = new ReviewImageAdapter(this.mReviewImageList, this.context);
        mReviewImageListRecycleView.setAdapter(mReviewImageListAdapter);

        mReviewImageListRecycleView.postDelayed(() -> {
            mReviewImageListRecycleView.scrollToPosition(currentPosition);
        }, 200);
    }

    public void setCurrentItemViewPager(int currentPosition) {
        setHighlightSelectedItemImageList(currentPosition);
        mViewImageViewPager.setCurrentItem(currentPosition);
    }

    private void setHighlightSelectedItemImageList(int position) {
        List<ImageItem> tempList = new ArrayList<>();
        for (ImageItem item : mReviewImageList) {
            try {
                tempList.add(item.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        tempList.get(prevHighlightItem).isSelected = false;
        tempList.get(position).isSelected = true;
        if (position - 1 >= 0)
            tempList.get(position - 1).isSelected = false;
        if (position + 1 < tempList.size())
            tempList.get(position + 1).isSelected = false;
        mReviewImageListAdapter.setData(tempList);
        prevHighlightItem = position;
    }
}
