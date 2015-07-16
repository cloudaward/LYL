package com.cloudaward.lyl.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {

  public static void putString(SharedPreferences prefs, String key, String value) {
    Editor editor = prefs.edit();
    editor.putString(key, value);
    editor.apply();
  }
  
  public static String getString(SharedPreferences prefs, String key, String defValue) {
    String value = prefs.getString(key, defValue);
    return value;
  }
  
}
