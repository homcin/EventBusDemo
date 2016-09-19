package com.grace.eventbusdemo.network;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.grace.eventbusdemo.entity.Benefit;
import com.grace.eventbusdemo.event.GirlsLoadedEvent;
import com.grace.eventbusdemo.event.LoadFailureEvent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.greenrobot.event.EventBus;

public class HttpUtil {

	public static final int RESPONSE = 1;
	public static final int FAILURE = 2;
	public static final String KEY = "RESPONSE";

	public static void sendHttpRequestWithEventBus(final String address) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					Log.d("response", response.toString());
					Gson gson = new Gson();
					Benefit benefit = gson.fromJson(response.toString(), Benefit.class);
					EventBus.getDefault().post(new GirlsLoadedEvent(benefit.getResults()));
				} catch (Exception e) {
					Log.d("response", e.getMessage());
					EventBus.getDefault().post(new LoadFailureEvent(e.getMessage()));
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	public static void sendHttpRequestWithHandler(final String address, final Handler mUiHandler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if (mUiHandler != null) {
						Message msg = new Message();
						msg.what = RESPONSE;
						msg.obj = response.toString();
						mUiHandler.sendMessage(msg);
					}
				} catch (Exception e) {
					if (mUiHandler != null) {
						Message msg = new Message();
						msg.what = FAILURE;
						msg.obj = e.getMessage();
						mUiHandler.sendMessage(msg);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
	
	public static void sendHttpRequestWithCallback(final String address,
			final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if (listener != null) {
						listener.onResponse(response.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onFailure(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	public static void sendHttpRequestWithBroadcast(final String address, final Context context) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					Intent intent = new Intent("com.grace.action.girlsbroadcast");
					intent.putExtra(KEY, response.toString());
					context.sendBroadcast(intent);
				} catch (Exception e) {
					Intent intent = new Intent("com.grace.action.girlsbroadcast");
					intent.putExtra(KEY, e.getMessage());
					context.sendBroadcast(intent);
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}