package com.hainguyen.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.OnViewDragListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.hainguyen.myapplication.MainActivity;
import com.hainguyen.myapplication.R;
import com.hainguyen.myapplication.model.ImageItem;
import com.hainguyen.myapplication.ui.ViewPagerLayout;

import java.util.List;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private static String VIEWHOLDER_CONTAINERTEXTVIEW = "VIEWHOLDER_CONTAINERTEXTVIEW";

    // Context object
    private Context context;

    // Array of images
    private List<ImageItem> images;

    // Layout Inflater
    private LayoutInflater mLayoutInflater;


    // Viewpager Constructor
    public ViewPagerAdapter(Context context, List<ImageItem> images) {
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // return the number of images
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((FrameLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.view_image_dialog_pager_item, container, false);
        ImageItem item = images.get(position);
        ViewHolder viewHolder = new ViewHolder(context, itemView, item, position, images.size());

        // Adding the View
        Objects.requireNonNull(container).addView(viewHolder.itemViewLayout);

        return viewHolder.itemViewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    public static class ViewHolder {
        View itemViewLayout;
        PhotoView photoView;
        TextView textView;
        LinearLayout containerTextView;
        ImageItem imageItem;
        static boolean isHideContainerTextView = false;
        static int listSize = 0;
        private OnViewPagerSwipeListener onViewPagerSwipeListener;

        OnSingleFlingListener onSingleFlingListener = new OnSingleFlingListener() {
            @Override
            public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
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
                            /*if (diffY > 0) {
                                //onSwipeListener.swipeToBottom();
                            } else {
                                //onSwipeListener.swipeToTop();
                            }*/
                            isHideContainerTextView = !isHideContainerTextView;
                            hideGroupContainerTextView();
                            onViewPagerSwipeListener.onViewPagerSwipeListener(isHideContainerTextView);

                            result = true;
                        }
                    }
                } catch (Exception ex) {
                }
                return result;
            }
        };

        public ViewHolder(Context context, View itemView, ImageItem item, int position, int size) {
            this.itemViewLayout = itemView;
            this.imageItem = item;

            // referencing the image view from the item.xml file
            this.photoView = (PhotoView) this.itemViewLayout.findViewById(R.id.view_image_dialog_pager_item_photoview);
            this.textView = (TextView) this.itemViewLayout.findViewById(R.id.view_image_dialog_pager_item_textview);
            this.containerTextView = this.itemViewLayout.findViewById(R.id.view_image_dialog_pager_item_textview_container);
            onViewPagerSwipeListener = (OnViewPagerSwipeListener) context;

            this.containerTextView.setTag(VIEWHOLDER_CONTAINERTEXTVIEW + position);
            listSize = size;
            hideContainerTextView(this.containerTextView);

            //Vì ViewPager kết hợp với Zoompho nên detect top, bottom ở đây
            photoView.setOnSingleFlingListener(onSingleFlingListener);

            // setting the image in the imageView
            Glide.with(context).load(this.imageItem.url).into(photoView);

            textView.setText(item.name);
        }

        private void hideContainerTextView(View view) {
            if (isHideContainerTextView) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
        }

        private void hideGroupContainerTextView() {
            if (itemViewLayout.getParent() != null && itemViewLayout.getParent() instanceof ViewPagerLayout) {
                ViewPagerLayout viewParent = (ViewPagerLayout) itemViewLayout.getParent();
                int viewCount = viewParent.getChildCount();
                for (int position = 0; position < listSize; position++) {
                    View view = viewParent.findViewWithTag(VIEWHOLDER_CONTAINERTEXTVIEW + position);
                    if (view != null) {
                        if (view.getId() == R.id.view_image_dialog_pager_item_textview_container) {
                            hideContainerTextView(view);
                        }
                        viewCount--;
                    }
                    if (viewCount == 0) break;
                }
            }
        }

        public interface OnViewPagerSwipeListener {
            void onViewPagerSwipeListener(boolean isHideContainerTextView);
        }
    }


}