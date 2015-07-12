package com.cloudaward.lyl.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ActivityUtils {

  public static void startActivity(Activity activity, Class<?> toActivityClass) {
    Intent intent = new Intent();
    intent.setClass(activity, toActivityClass);
    activity.startActivity(intent);
  }

  @SuppressLint("NewApi")
  public static void startActivityForResult(Activity activity, Class<?> toActivityClass, int requestCode, Bundle options) {
    Intent intent = new Intent();
    intent.setClass(activity, toActivityClass);
    activity.startActivityForResult(intent, requestCode, options);
  }
  
}
