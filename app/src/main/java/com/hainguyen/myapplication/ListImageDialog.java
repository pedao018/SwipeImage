package com.hainguyen.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.ImageButton;

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
    protected ViewPagerLayout mViewImageViewPager;
    private ViewPagerAdapter mViewImageViewPagerAdapter;
    private RecyclerView mReviewImageListRecycleView;
    private ReviewImageAdapter mReviewImageListAdapter;
    private ImageButton mImgBtnBack;
    private List<ImageItem> mReviewImageList;
    private int prevHighlightItem; //Dùng để xác định item được chọn trước đó trong mReviewImageList, dùng để xóa màu selected
    public boolean isHideContainerTextView = false;
    private OnCloseDialog onCloseDialog;
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
        onCloseDialog = (OnCloseDialog) context;

        mViewImageViewPager = (ViewPagerLayout) findViewById(R.id.view_image_dialog_viewpager);
        mReviewImageListRecycleView = (RecyclerView) findViewById(R.id.view_image_dialog_review_list);
        mViewImageViewPagerAdapter = new ViewPagerAdapter(this.context, this.mReviewImageList);
        mImgBtnBack = (ImageButton) findViewById(R.id.view_image_dialog_imgbtn_back);

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

        mImgBtnBack.setOnClickListener(view -> {
            this.dismiss();
            onCloseDialog.onCloseDialog();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onCloseDialog.onCloseDialog();
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

    protected void hideImgBtnBack(boolean isHideContainerTextView) {
        this.isHideContainerTextView = isHideContainerTextView;
        if (isHideContainerTextView) {
            this.mImgBtnBack.setVisibility(View.GONE);
            this.mReviewImageListRecycleView.setVisibility(View.GONE);
        } else {
            this.mImgBtnBack.setVisibility(View.VISIBLE);
            this.mReviewImageListRecycleView.setVisibility(View.VISIBLE);
        }
    }

    interface OnCloseDialog {
        void onCloseDialog();
    }
}
