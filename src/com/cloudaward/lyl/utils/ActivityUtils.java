package com.cloudaward.lyl.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudaward.lyl.R;

@SuppressWarnings("deprecation")
public class ActivityUtils {

  public static void startActivity(Context activity, Class<?> toActivityClass) {
    Intent intent = new Intent();
    intent.setClass(activity, toActivityClass);
    activity.startActivity(intent);
  }

  @SuppressLint("NewApi")
  public static void startActivityForResult(ActionBarActivity activity, Class<?> toActivityClass, int requestCode, Bundle options) {
    Intent intent = new Intent();
    intent.setClass(activity, toActivityClass);
    activity.startActivityForResult(intent, requestCode, options);
  }
  
  @SuppressLint("InflateParams")
  public static void initGeneralActionBar(final ActionBarActivity activity, String title) {
    ActionBar actionBar = activity.getSupportActionBar();
    View view = LayoutInflater.from(activity).inflate(R.layout.actionbar_general, null);
    ActionBar.LayoutParams params =
        new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
    TextView titleTextView = (TextView) view.findViewById(R.id.tv_title);
    titleTextView.setText(title);
    ImageView backImageView = (ImageView) view.findViewById(R.id.iv_back);
    backImageView.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        activity.finish();
      }
    });
    actionBar.setCustomView(view, params);
    actionBar.setDisplayShowCustomEnabled(true);
  }
  
}
