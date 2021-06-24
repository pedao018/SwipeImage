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
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.hainguyen.myapplication.ListImageDialog;
import com.hainguyen.myapplication.R;
import com.hainguyen.myapplication.WebItem;

import java.util.List;

public class WebAdapter extends RecyclerView.Adapter<WebAdapter.DataViewHolder> {

    private List<WebItem> dataSet;
    private Context context;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    public WebAdapter(List<WebItem> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }


    @NonNull
    @Override
    public WebAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.web_item, parent, false);
        return new WebAdapter.DataViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull WebAdapter.DataViewHolder dataViewHolder, int position) {
        WebItem item = dataSet.get(position);
        dataViewHolder.textViewItem.setText(item.name);
        Glide.with(context).load(item.url).into(dataViewHolder.imageViewItem);
        dataViewHolder.container.setOnClickListener(view -> {
            ListImageDialog imageDialog = new ListImageDialog(context, dataSet, position);
            imageDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imageViewItem;
        protected TextView textViewItem;
        protected LinearLayout container;


        public DataViewHolder(View itemView, int viewType) {
            super(itemView);
            imageViewItem = (ImageView) itemView.findViewById(R.id.imageViewItem);
            textViewItem = (TextView) itemView.findViewById(R.id.textViewItem);
            container = (LinearLayout) itemView.findViewById(R.id.item_container);
        }
    }
}