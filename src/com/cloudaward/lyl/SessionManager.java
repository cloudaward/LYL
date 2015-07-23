package com.cloudaward.lyl;

import java.util.HashMap;
import java.util.Map;

import com.cloudaward.lyl.utils.SharedPreferencesUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

  private static final String PREFS_SESSION = "prefs_session";

  private SharedPreferences mSharedPreferences;

  private Editor mEditor;

  private Context mContext;

  private static final String IS_LOGIN = "IsLoggedIn";

  public static final String KEY_USERNAME = "username";

  public SessionManager(Context context) {
    this.mContext = context;
    this.mSharedPreferences = SharedPreferencesUtils.getPreferences(context.getApplicationContext(), PREFS_SESSION);
    this.mEditor = mSharedPreferences.edit();
  }

  public void createLoginSession(String username) {
    mEditor.putBoolean(IS_LOGIN, true);
    mEditor.putString(KEY_USERNAME, username);
    mEditor.commit();
  }

  public boolean checkLogin() {
    if (!isLoggedin()) {
      Intent intent = new Intent(mContext, LoginActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      mContext.startActivity(intent);
      return true;
    }
    return false;
  }

  public boolean isLoggedin() {
    return mSharedPreferences.getBoolean(IS_LOGIN, false);
  }

  public Map<String, String> getLoginDetails() {
    Map<String, String> map = new HashMap<String, String>();
    map.put(KEY_USERNAME, mSharedPreferences.getString(KEY_USERNAME, null));
    return map;
  }

  public void logout() {
    mEditor.clear();
    mEditor.commit();

    Intent intent = new Intent(mContext, LoginActivity.class);
    // Closing all the Activities
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    // Add new Flag to start new Activity
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    // Staring Login Activity
    mContext.startActivity(intent);
  }
}
