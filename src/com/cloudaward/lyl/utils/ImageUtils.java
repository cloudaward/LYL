package com.cloudaward.lyl.utils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.cloudaward.lyl.MainApplication;
import com.cloudaward.lyl.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

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
  
  public static void displayAvatar(final ImageView imgView, String imageUrl) {
    ImageRequest request = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
      @Override
      public void onResponse(Bitmap bitmap) {
        RoundedBitmapDrawable drawable = ImageUtils.getCircleBitmap(MainApplication.getInstance().getApplicationContext(), bitmap);
        imgView.setImageDrawable(drawable);
      }
    }, 0, 0, null, new Response.ErrorListener() {
      public void onErrorResponse(VolleyError error) {
        imgView.setImageResource(R.drawable.ic_avatar);
      }
    });
    MainApplication.getInstance().getRequestQueue().add(request);
  }

}
