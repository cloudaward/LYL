package com.cloudaward.lyl;

import java.util.Map;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cloudaward.lyl.utils.SharedPreferencesUtils;

public class MainApplication extends Application {

  public static final String TAG = MainActivity.class.getSimpleName();

  private static final String SET_COOKIE_KEY = "Set-Cookie";
  private static final String COOKIE_KEY = "Cookie";
  private static final String SESSION_COOKIE = "u";

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

  public final void checkSessionCookie(Map<String, String> headers) {
    if (headers.containsKey(SET_COOKIE_KEY)
        && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
      String cookie = headers.get(SET_COOKIE_KEY);
      if (cookie.length() > 0) {
        String[] splitCookie = cookie.split(";");
        String[] splitSessionId = splitCookie[0].split("=");
        cookie = splitSessionId[1];
        SharedPreferencesUtils.putString(getApplicationContext(), SESSION_COOKIE, cookie);
      }
    }
  }

  /**
   * Adds session cookie to headers if exists.
   * 
   * @param headers
   */
  public final void addSessionCookie(Map<String, String> headers) {
    String sessionId = SharedPreferencesUtils.getString(getApplicationContext(), SESSION_COOKIE, "");
    if (sessionId.length() > 0) {
      StringBuilder builder = new StringBuilder();
      builder.append(SESSION_COOKIE);
      builder.append("=");
      builder.append(sessionId);
      if (headers.containsKey(COOKIE_KEY)) {
        builder.append("; ");
        builder.append(headers.get(COOKIE_KEY));
      }
      headers.put(COOKIE_KEY, builder.toString());
    }
  }

}
