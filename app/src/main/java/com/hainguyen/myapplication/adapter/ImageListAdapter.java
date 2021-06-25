package com.hainguyen.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hainguyen.myapplication.ListImageDialog;
import com.hainguyen.myapplication.R;
import com.hainguyen.myapplication.model.ImageItem;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.DataViewHolder> {

    private List<ImageItem> dataSet;
    private Context context;
    private OnImageListListener onImageListListener;

    public ImageListAdapter(List<ImageItem> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
        onImageListListener = (OnImageListListener) context;
    }

    @NonNull
    @Override
    public ImageListAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
        return new ImageListAdapter.DataViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageListAdapter.DataViewHolder dataViewHolder, int position) {
        ImageItem item = dataSet.get(position);
        dataViewHolder.textView.setText(item.name);
        Glide.with(context).load(item.url).into(dataViewHolder.imageView);
        dataViewHolder.container.setOnClickListener(view -> {
            onImageListListener.onImageListClickListener(context, dataSet, position);
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imageView;
        protected TextView textView;
        protected LinearLayout container;


        public DataViewHolder(View itemView, int viewType) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_list_item_image);
            textView = (TextView) itemView.findViewById(R.id.image_list_item_textview);
            container = (LinearLayout) itemView.findViewById(R.id.image_list_item_container);
        }
    }

    public interface OnImageListListener {
        void onImageListClickListener(@NonNull Context context, List<ImageItem> dataSet, int currentPosition);
    }
}