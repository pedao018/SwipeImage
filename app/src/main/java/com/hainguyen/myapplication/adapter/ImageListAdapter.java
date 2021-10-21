package com.hainguyen.myapplication.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hainguyen.myapplication.ListImageDialog;
import com.hainguyen.myapplication.R;
import com.hainguyen.myapplication.model.DownloadImageTask;
import com.hainguyen.myapplication.model.DownloadImageTask_2;
import com.hainguyen.myapplication.model.ImageItem;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
        if (item.url.equals("https://pda01.esales.vn/Vedan_IMG/B2NPPDTH01_B2SM180621.jpg")) {
            //new DownloadImageTask(dataViewHolder.imageView).execute(item.url);
            new DownloadImageTask_2(context, dataViewHolder.imageView).execute(item.url);
        } else
            Glide.with(context).load(item.url).placeholder(R.drawable.loading_animation).into(dataViewHolder.imageView);
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