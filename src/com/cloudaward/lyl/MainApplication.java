package com.cloudaward.lyl;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.text.TextUtils;

public class MainApplication extends Application {

  public static final String TAG = MainActivity.class.getSimpleName();

  public static MainApplication instance;
  private RequestQueue mRequestQueue;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;

  }

  public synchronized static MainApplication getInstance() {
    return instance;
  }

  public RequestQueue getRequestQueue() {
    if (mRequestQueue == null) {
      mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    return mRequestQueue;
  }

  public <T> void addToRequestQueue(Request<T> request) {
    request.setTag(TAG);
    getRequestQueue().add(request);
  }

  public <T> void addToRequestQueue(Request<T> request, String tag) {
    request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
    getRequestQueue().add(request);
  }

  public void cancelPendingRequest(Object tag) {
    if (mRequestQueue != null) {
      mRequestQueue.cancelAll(tag);
    }
  }

}
