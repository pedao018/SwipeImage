package com.hainguyen.myapplication.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hainguyen.myapplication.R;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class DownloadImageTask_2 extends AsyncTask<String, Void, String> {
    ImageView bmImage;
    Context context;

    public DownloadImageTask_2(Context context, ImageView bmImage) {
        this.context = context;
        this.bmImage = bmImage;
    }

    protected String doInBackground(String... urls) {
        String urldisplay = urls[0];
        try {
            connectURL(urldisplay);
        } catch (Exception e) {
            Log.e("Error", urldisplay + " " + e.getMessage());
            e.printStackTrace();
        }
        return urldisplay;
    }

    protected void onPostExecute(String url) {
        Glide.with(context).load(url).placeholder(R.drawable.loading_animation).into(bmImage);
    }

    public void connectURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);

            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            HttpsURLConnection connection = (HttpsURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}