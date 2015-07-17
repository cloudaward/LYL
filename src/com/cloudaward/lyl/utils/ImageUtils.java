package com.cloudaward.lyl.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

public class ImageUtils {

  public static RoundedBitmapDrawable getCircleBitmap(Context context, Bitmap src) {
    Bitmap squareBitmap;
    if (src.getWidth() >= src.getHeight()) {
      squareBitmap = Bitmap.createBitmap(src, src.getWidth() / 2 - src.getHeight() / 2, 0, src.getHeight(), src.getHeight());
    } else {
      squareBitmap = Bitmap.createBitmap(src, 0, src.getHeight() / 2 - src.getWidth() / 2, src.getWidth(), src.getWidth());
    }
    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), squareBitmap);
    roundedBitmapDrawable.setCornerRadius(squareBitmap.getWidth() / 2);
    roundedBitmapDrawable.setAntiAlias(true);
    return roundedBitmapDrawable;
  }

}
