package com.cloudaward.lyl.utils;

import java.util.UUID;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class SystemUtils {

  public static TelephonyManager getTelephonyManager(Context context) {
    TelephonyManager manager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return manager;
  }

  public static String getUUID(Context context) {
    String deviceId = getTelephonyManager(context).getDeviceId();
    String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    UUID uuid = new UUID(androidId.hashCode(), deviceId.hashCode() << 32);
    return uuid.toString();
  }


  public static String getSimSerialNumber(Context context) {
    String simSerialNumber = getTelephonyManager(context).getSimSerialNumber();
    return simSerialNumber;
  }

  public static String getDeviceId(Context context) {
    String deviceId = getTelephonyManager(context).getDeviceId();
    return deviceId;
  }

  public static String getAppVersion(Context context) {
    PackageManager manager = context.getPackageManager();
    PackageInfo packageInfo = null;
    try {
      packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
      return packageInfo.versionName;
    } catch (NameNotFoundException e) {
      e.printStackTrace();
    }
    return "not_found";
  }

  public static String getScreenSize(Context context) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    return metrics.widthPixels + "x" + metrics.heightPixels;
  }

}
