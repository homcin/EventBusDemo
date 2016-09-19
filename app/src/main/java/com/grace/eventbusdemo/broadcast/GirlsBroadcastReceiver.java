package com.grace.eventbusdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.grace.eventbusdemo.activity.GirlsActivity;
import com.grace.eventbusdemo.network.HttpUtil;

/**
 * Created by Administrator on 2016/9/19.
 */
public class GirlsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String response = intent.getStringExtra(HttpUtil.KEY);
        Message msg = new Message();
        msg.what = HttpUtil.RESPONSE;
        msg.obj = response;
        ((GirlsActivity)context).mUiHandler.sendMessage(msg);
    }
}
