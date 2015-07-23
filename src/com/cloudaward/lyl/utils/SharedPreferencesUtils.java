package com.cloudaward.lyl.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
  
  public static SharedPreferences getPreferences(Context context, String name) {
    return context.getSharedPreferences(name, Context.MODE_PRIVATE);
  }
  
}
