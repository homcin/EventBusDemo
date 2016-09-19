package com.grace.eventbusdemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.grace.eventbusdemo.R;
import com.grace.eventbusdemo.adapter.GirlsAdapter;
import com.grace.eventbusdemo.entity.Benefit;
import com.grace.eventbusdemo.entity.Girl;
import com.grace.eventbusdemo.event.GirlsLoadedEvent;
import com.grace.eventbusdemo.event.LoadFailureEvent;
import com.grace.eventbusdemo.network.HttpCallbackListener;
import com.grace.eventbusdemo.network.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit2.http.Path;

public class GirlsActivity extends AppCompatActivity {

    public static final String MODE = "mode";

    RecyclerView rvGirls;
    private static List<Girl> girls;
    private static GirlsAdapter girlsAdapter;
    private static Gson gson = new Gson();

    public Handler mUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HttpUtil.RESPONSE:
                    Benefit benefit = gson.fromJson((String) msg.obj, Benefit.class);
                    girls.clear();
                    girls.addAll(benefit.getResults());
                    girlsAdapter.notifyDataSetChanged();
                    break;
                case HttpUtil.FAILURE:
                    break;
            }
        }
    };

    public static void actionStart(Context context, int mode) {
        Intent intent = new Intent(context, GirlsActivity.class);
        intent.putExtra(MODE, mode);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girls);
        int mode = getIntent().getIntExtra(MODE, 1);
        EventBus.getDefault().register(this);
        rvGirls = (RecyclerView) findViewById(R.id.rv_girls);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        rvGirls.setLayoutManager(layoutManager);
        girls = new ArrayList<>();
        girlsAdapter = new GirlsAdapter(this, girls);
        rvGirls.setAdapter(girlsAdapter);
        loadData(mode);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static class GirlsBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(HttpUtil.KEY);
            Benefit benefit = gson.fromJson(response, Benefit.class);
            girls.clear();
            girls.addAll(benefit.getResults());
            girlsAdapter.notifyDataSetChanged();
        }
    }

    private void loadData(int mode) {
        String address = "http://gank.io/api/data/福利/20/1";
        switch (mode) {
            case 1:
                HttpUtil.sendHttpRequestWithEventBus(address);
                break;
            case 2:
                HttpUtil.sendHttpRequestWithHandler(address, mUiHandler);
                break;
            case 3:
                HttpUtil.sendHttpRequestWithCallback(address, new HttpCallbackListener() {
                    @Override
                    public void onResponse(String response) {
                        Benefit benefit = gson.fromJson(response, Benefit.class);
                        girls.clear();
                        girls.addAll(benefit.getResults());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                girlsAdapter.notifyDataSetChanged();
                            }
                        });

                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
                break;
            case 4:
                HttpUtil.sendHttpRequestWithBroadcast(address, this);
                break;
        }
    }

    public void onEventMainThread(GirlsLoadedEvent event) {
        girls.clear();
        girls.addAll(event.girls);
        girlsAdapter.notifyDataSetChanged();
    }

    public void onEventMainThread(LoadFailureEvent event) {
    }

}
