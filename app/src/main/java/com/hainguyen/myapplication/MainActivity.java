package com.hainguyen.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.hainguyen.myapplication.adapter.ImageListAdapter;
import com.hainguyen.myapplication.adapter.ReviewImageAdapter;
import com.hainguyen.myapplication.adapter.ViewPagerAdapter;
import com.hainguyen.myapplication.model.ImageItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements ImageListAdapter.OnImageListListener, ReviewImageAdapter.OnReviewImageListener, ViewPagerAdapter.ViewHolder.OnViewPagerSwipeListener {

    private RecyclerView imageListRecyclerView;
    private ImageListAdapter imageListAdapter;
    List<ImageItem> imageList;
    ListImageDialog imageDialog;

    private static String IMAGE_DIALOG_CURRENT_SELECTED_IMAGE = "IMAGE_DIALOG_CURRENT_SELECTED_IMAGE";
    private static String IMAGE_DIALOG_IS_HIDE_CONTAINER_TEXTVIEW = "IMAGE_DIALOG_IS_HIDE_CONTAINER_TEXTVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageListRecyclerView = (RecyclerView) findViewById(R.id.image_list_recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        imageListRecyclerView.setLayoutManager(layoutManager);
        imageListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        imageListRecyclerView.setFocusable(false);

        imageList = new ArrayList<>();
        imageList.add(new ImageItem("Ảnh 1", "https://i.pinimg.com/564x/7b/55/d3/7b55d36efc468c573da95d90ef5764b9.jpg"));
        imageList.add(new ImageItem("Ảnh 2", "https://i.pinimg.com/564x/d2/1f/cc/d21fcc66de988ed964930394a6fa2b26.jpg"));
        imageList.add(new ImageItem("Ảnh 3", "https://i.pinimg.com/564x/7a/58/d9/7a58d9b1d453816941ffb99ad55edf7a.jpg"));
        imageList.add(new ImageItem("Ảnh 4", "https://i.pinimg.com/564x/64/df/3a/64df3aee6ea76d6dd1fea8bbe535988b.jpg"));
        imageList.add(new ImageItem("Ảnh 5", "https://i.pinimg.com/564x/32/f4/1c/32f41cc05b17e3d52a65fdaadf575e88.jpg"));
        imageList.add(new ImageItem("Ảnh 6", "https://i.pinimg.com/564x/f0/4b/7f/f04b7fd585d1e5638fb5e6664446ee3f.jpg"));
        imageList.add(new ImageItem("Ảnh 7", "https://i.pinimg.com/564x/4e/a9/1f/4ea91f60e439dd94c37df106c6682a5f.jpg"));
        imageList.add(new ImageItem("Ảnh 8", "https://i.pinimg.com/564x/2b/0e/ae/2b0eae86b82173d8fb74820377016757.jpg"));
        imageList.add(new ImageItem("Ảnh 9", "https://i.pinimg.com/564x/90/07/95/900795e4b413f8b019e3570288df0dc6.jpg"));
        imageList.add(new ImageItem("Ảnh 10", "https://i.pinimg.com/564x/b7/ba/56/b7ba569d599dcd462e093d2b799e8333.jpg"));
        imageList.add(new ImageItem("Ảnh 11", "https://i.pinimg.com/564x/cf/fa/83/cffa83ebe67a00b3dfead14ce35060e9.jpg"));
        imageList.add(new ImageItem("Ảnh 12", "https://i.pinimg.com/564x/c3/61/8f/c3618f3846dae71a3653d9c5ff70a25e.jpg"));
        imageList.add(new ImageItem("Ảnh 13", "https://i.pinimg.com/564x/99/de/52/99de52e1612dfd4be0eacdc83fe1d8ad.jpg"));
        imageList.add(new ImageItem("Ảnh 14", "https://i.pinimg.com/564x/aa/10/f0/aa10f082b3938e8909ae737e851d5217.jpg"));
        imageList.add(new ImageItem("Ảnh 15", "https://i.pinimg.com/564x/f6/fa/58/f6fa58490961b62ebc0a3b422c6a4312.jpg"));
        imageList.add(new ImageItem("Ảnh 16", "https://i.pinimg.com/564x/06/fd/e4/06fde4241f713ac2b8a462017896a277.jpg"));
        imageList.add(new ImageItem("Ảnh 17", "https://i.pinimg.com/564x/67/37/4a/67374a38f93b461811a207e94ddabbcb.jpg"));
        imageList.add(new ImageItem("Ảnh 18", "https://i.pinimg.com/564x/3a/01/d5/3a01d5aa61dda90816ca150e90874769.jpg"));
        imageList.add(new ImageItem("Ảnh 19", "https://i.pinimg.com/564x/6d/b8/b1/6db8b110c64b93f769f977b5fcad7f62.jpg"));
        imageList.add(new ImageItem("Ảnh 20", "https://i.pinimg.com/564x/29/62/81/2962810a29877d0754c95a06e3effbeb.jpg"));
        imageList.add(new ImageItem("Ảnh 21", "https://i.pinimg.com/564x/4e/cc/bc/4eccbc159e997d552088f1853440ba23.jpg"));
        imageList.add(new ImageItem("Ảnh 22", "https://i.pinimg.com/564x/63/78/79/637879031c18ab781919aabcb743020f.jpg"));

        imageListAdapter = new ImageListAdapter(imageList, this);
        imageListRecyclerView.setAdapter(imageListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imageDialog != null)
            imageDialog.dismiss();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (imageDialog != null) {
            //Lưu vị trí item được chọn (Chọn do người dùng), dùng cho xoay màn hình lưu giữ data lại
            outState.putInt(IMAGE_DIALOG_CURRENT_SELECTED_IMAGE, imageDialog.mViewImageViewPager.getCurrentItem());
            outState.putBoolean(IMAGE_DIALOG_IS_HIDE_CONTAINER_TEXTVIEW, imageDialog.isHideContainerTextView);
        }
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.get(IMAGE_DIALOG_CURRENT_SELECTED_IMAGE) != null) {
            int currentPosition = savedInstanceState.getInt(IMAGE_DIALOG_CURRENT_SELECTED_IMAGE);
            boolean isHideContainerTextView = false;
            if (savedInstanceState.get(IMAGE_DIALOG_IS_HIDE_CONTAINER_TEXTVIEW) != null)
                isHideContainerTextView = savedInstanceState.getBoolean(IMAGE_DIALOG_IS_HIDE_CONTAINER_TEXTVIEW);
            showDialog(this, imageList, currentPosition, isHideContainerTextView);

        }

    }

    @Override
    public void onImageListClickListener(@NonNull Context context, List<ImageItem> dataSet, int currentPosition) {
        showDialog(context, dataSet, currentPosition, false);
    }

    @Override
    public void onReviewImageClickListener(int position) {
        this.imageDialog.setCurrentItemViewPager(position);
    }

    private void showDialog(@NonNull Context context, List<ImageItem> dataSet, int currentPosition, boolean isHideContainerTextView) {
        this.imageDialog = new ListImageDialog(context, dataSet, currentPosition);
        imageDialog.hideImgBtnBack(isHideContainerTextView);
        this.imageDialog.show();
    }

    @Override
    public void onViewPagerSwipeListener(boolean isHideContainerTextView) {
        if (imageDialog != null) {
            imageDialog.hideImgBtnBack(isHideContainerTextView);
        }
    }
}