package com.hainguyen.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;

import com.hainguyen.myapplication.adapter.WebAdapter;
import com.hainguyen.myapplication.ui.SwipeDetectModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public RecyclerView webViewRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webViewRecyclerView = (RecyclerView) findViewById(R.id.webview_recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        webViewRecyclerView.setLayoutManager(layoutManager);
        webViewRecyclerView.setItemAnimator(new DefaultItemAnimator());
        webViewRecyclerView.setFocusable(false);

        List<WebItem> list = new ArrayList<>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("DKN News", "https://i.pinimg.com/564x/52/82/ea/5282ea1d4ede83b5f8f2f842073b476a.jpg");
        map.put("DKN News 2", "https://i.pinimg.com/564x/f8/26/63/f82663c95ccde80628e34594c09a5540.jpg");
        map.put("DKN News 3", "https://i.pinimg.com/564x/42/7a/1e/427a1e601c0b01751a58f0a718b38782.jpg");
        map.put("Cat", "https://i.pinimg.com/564x/28/2d/7f/282d7f92b7c162a21afd5002383a3eff.jpg");
        map.put("Cat 2", "https://i.pinimg.com/564x/e9/db/ac/e9dbac19f668cd8f44ac50abf2879f85.jpg");
        map.put("Cat 3", "https://i.pinimg.com/564x/73/3d/7e/733d7e1f12eed7c4527ef836954a5aa6.jpg");
        map.put("City", "https://i.pinimg.com/564x/8d/4f/e7/8d4fe7d83b1781c1ec979d2bb0767e8d.jpg");
        map.put("City 2", "https://i.pinimg.com/564x/72/9c/db/729cdb982d6cd58dacc7009f33057770.jpg");
        map.put("Galaxy ", "https://i.pinimg.com/564x/cb/cb/12/cbcb122c1bdd2e1ab2f986a03277e5a4.jpg");
        map.put("Galaxy 2", "https://i.pinimg.com/564x/13/fe/df/13fedf366e39e51f91962d9f8ac075e5.jpg");
        map.put("Galaxy 3", "https://i.pinimg.com/564x/13/fe/df/13fedf366e39e51f91962d9f8ac075e5.jpg");
        map.put("Galaxy 4", "https://i.pinimg.com/564x/13/fe/df/13fedf366e39e51f91962d9f8ac075e5.jpg");
        map.put("Galaxy 5", "https://i.pinimg.com/564x/13/fe/df/13fedf366e39e51f91962d9f8ac075e5.jpg");
        map.put("Galaxy 6", "https://i.pinimg.com/564x/13/fe/df/13fedf366e39e51f91962d9f8ac075e5.jpg");
        map.put("Galaxy 7", "https://i.pinimg.com/564x/13/fe/df/13fedf366e39e51f91962d9f8ac075e5.jpg");
        //Elements can traverse in any order
        for (Map.Entry m : map.entrySet()) {
            list.add(new WebItem(m.getKey().toString(), m.getValue().toString()));
        }
        setAdapterJobType(list);


    }

    public void setAdapterJobType(List<WebItem> list) {
        WebAdapter webAdapter = new WebAdapter(list, this);
        webViewRecyclerView.setAdapter(webAdapter);
    }
}