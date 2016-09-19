package com.grace.eventbusdemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grace.eventbusdemo.R;
import com.grace.eventbusdemo.network.HttpUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEventBus;
    Button btnHandler;
    Button btnCallback;
    Button btnBoradCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEventBus = (Button) findViewById(R.id.btn_event_bus);
        btnHandler = (Button) findViewById(R.id.btn_handler);
        btnCallback = (Button) findViewById(R.id.btn_callback);
        btnBoradCast = (Button) findViewById(R.id.btn_broadcast);
        btnEventBus.setOnClickListener(this);
        btnHandler.setOnClickListener(this);
        btnCallback.setOnClickListener(this);
        btnBoradCast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_event_bus:
                GirlsActivity.actionStart(this, 1);
                break;
            case R.id.btn_handler:
                GirlsActivity.actionStart(this, 2);
                break;
            case R.id.btn_callback:
                GirlsActivity.actionStart(this, 3);
                break;
            case R.id.btn_broadcast:
                GirlsActivity.actionStart(this, 4);
                break;
        }
    }

}
