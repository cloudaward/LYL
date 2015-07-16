package com.cloudaward.lyl.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {
  
  public static SharedPreferences getPrefs(Context context) { 
    SharedPreferences prefs =  context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    return prefs;
  }

  public static void putString(Context context, String key, String value) {
    SharedPreferences prefs = getPrefs(context);
    Editor editor = prefs.edit();
    editor.putString(key, value);
    editor.apply();
  }
  
  public static String getString(Context context, String key, String defValue) {
    SharedPreferences prefs = getPrefs(context);
    String value = prefs.getString(key, defValue);
    return value;
  }
  
}
