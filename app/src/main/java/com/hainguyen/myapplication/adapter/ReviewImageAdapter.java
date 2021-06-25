package com.hainguyen.myapplication.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hainguyen.myapplication.ListImageDialog;
import com.hainguyen.myapplication.model.MyDiffUtilCallBack;
import com.hainguyen.myapplication.R;
import com.hainguyen.myapplication.model.ImageItem;

import java.util.List;

public class ReviewImageAdapter extends RecyclerView.Adapter<ReviewImageAdapter.DataViewHolder> {

    private List<ImageItem> dataSet;
    private Context context;
    private OnReviewImageListener onReviewImageListener;

    public ReviewImageAdapter(List<ImageItem> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
        onReviewImageListener = (OnReviewImageListener) context;
    }

    @NonNull
    @Override
    public ReviewImageAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image_dialog_review_list_item, parent, false);
        return new ReviewImageAdapter.DataViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewImageAdapter.DataViewHolder dataViewHolder, int position) {
        ImageItem item = dataSet.get(position);
        Glide.with(context).load(item.url).into(dataViewHolder.imageViewItem);
        if (item.isSelected) {
            dataViewHolder.selectedView.setVisibility(View.VISIBLE);
        } else {
            dataViewHolder.selectedView.setVisibility(View.INVISIBLE);
        }
        dataViewHolder.container.setOnClickListener(view -> {
            onReviewImageListener.onReviewImageClickListener(position);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if (key.equals(ImageItem.key)) {
                    ImageItem item = dataSet.get(position);
                    if (item.isSelected) {
                        holder.selectedView.setVisibility(View.VISIBLE);
                    } else {
                        holder.selectedView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setData(List<ImageItem> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffUtilCallBack(newData, dataSet));
        diffResult.dispatchUpdatesTo(this);
        dataSet.clear();
        dataSet.addAll(newData);
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imageViewItem;
        protected View selectedView;
        protected FrameLayout container;

        public DataViewHolder(View itemView, int viewType) {
            super(itemView);
            imageViewItem = (ImageView) itemView.findViewById(R.id.view_image_dialog_review_list_item_image);
            selectedView = (View) itemView.findViewById(R.id.view_image_dialog_review_list_item_selected);
            container = (FrameLayout) itemView.findViewById(R.id.view_image_dialog_review_list_item_container);
        }
    }

    public interface OnReviewImageListener {
        void onReviewImageClickListener(int position);
    }
}