package com.grace.eventbusdemo.network;

public interface HttpCallbackListener {

	void onResponse(String response);

	void onFailure(Exception e);

}
