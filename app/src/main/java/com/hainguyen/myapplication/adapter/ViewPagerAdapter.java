package com.hainguyen.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.OnViewDragListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.hainguyen.myapplication.R;
import com.hainguyen.myapplication.model.ImageItem;

import java.util.List;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    // Context object
    private Context context;

    // Array of images
    private List<ImageItem> images;

    // Layout Inflater
    private LayoutInflater mLayoutInflater;

    OnSingleFlingListener onSingleFlingListener;


    // Viewpager Constructor
    public ViewPagerAdapter(Context context, List<ImageItem> images) {
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onSingleFlingListener = (downEvent, moveEvent, velocityX, velocityY) -> {
            boolean result = false;
            try {
                float diffY = moveEvent.getY() - downEvent.getY();
                float diffX = moveEvent.getX() - downEvent.getX();
                // which was greater?  movement across Y or X?
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    /*// right or left swipe
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            Log.e("hahaha", "SwipeToRight");
                        } else {
                            Log.e("hahaha", "SwipeToLeft");
                        }
                        result = true;
                    }*/
                } else {
                    // up or down swipe
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            Log.e("hahaha", "SwipeToBottom");
                        } else {
                            Log.e("hahaha", "SwipeToTop");
                        }
                        result = true;
                    }
                }
            } catch (Exception ex) {
            }
            return result;
        };
    }

    @Override
    public int getCount() {
        // return the number of images
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.view_image_dialog_pager_item, container, false);

        // referencing the image view from the item.xml file
        PhotoView photoView = (PhotoView) itemView.findViewById(R.id.view_image_dialog_pager_item_photoview);

        //Vì ViewPager kết hợp với Zoompho nên detect top, bottom ở đây
        photoView.setOnSingleFlingListener(onSingleFlingListener);

        // setting the image in the imageView
        Glide.with(context).load(images.get(position).url).into(photoView);

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}