package com.cloudaward.lyl;

import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MainApplication extends Application {
  public static final String TAG = MainActivity.class.getSimpleName();
  private static final String SET_COOKIE_KEY = "Set-Cookie";
  private static final String COOKIE_KEY = "Cookie";
  public static final String PREFS_SESSION_COOKIE = "prefs_session_cookie";
  private static final String SESSION_COOKIE = "u";

  private static MainApplication mInstance;

  private RequestQueue mRequestQueue;

  private SharedPreferences mSharedPreferences;

  @Override
  public void onCreate() {
    super.onCreate();
    mInstance = this;
    mSharedPreferences = getSharedPreferences(PREFS_SESSION_COOKIE, Context.MODE_PRIVATE);
    mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    initImageLoader(getApplicationContext());
  }

  private static void initImageLoader(Context context) {
    // This configuration tuning is custom. You can tune every option, you may tune some of them,
    // or you can create default configuration by
    // ImageLoaderConfiguration.createDefault(this);
    // method.
    ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
    config.threadPriority(Thread.NORM_PRIORITY - 2);
    config.denyCacheImageMultipleSizesInMemory();
    config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
    config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
    config.tasksProcessingOrder(QueueProcessingType.LIFO);
    config.writeDebugLogs(); // Remove for release app

    // Initialize ImageLoader with configuration.
    ImageLoader.getInstance().init(config.build());
  }

  public synchronized static MainApplication getInstance() {
    return mInstance;
  }

  public RequestQueue getRequestQueue() {
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
        Editor editor = mSharedPreferences.edit();
        editor.putString(SESSION_COOKIE, cookie);
        editor.commit();
      }
    }
  }

  /**
   * Adds session cookie to headers if exists.
   * 
   * @param headers
   */
  public final void addSessionCookie(Map<String, String> headers) {
    String sessionId = mSharedPreferences.getString(SESSION_COOKIE, "");
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

  public SharedPreferences getSharedPreferences() {
    return mSharedPreferences;
  }

}
