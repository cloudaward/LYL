package com.cloudaward.lyl.network;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.entity.ContentType;
import ch.boye.httpclientandroidlib.entity.mime.HttpMultipartMode;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntityBuilder;
import ch.boye.httpclientandroidlib.entity.mime.content.FileBody;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

public class MultipartRequest extends Request<JSONObject> {

  private static final String FILE_PART_NAME = "uploadFile";

  private static final String TAG = MultipartRequest.class.getSimpleName();

  private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
  private HttpEntity mEntity;
  private final Response.Listener<JSONObject> mListener;
  private final Map<String, String> mParams;

  public MultipartRequest(String url, Response.Listener<JSONObject> listener,
      Response.ErrorListener errorListener, File file, Map<String, String> params) {
    super(Method.POST, url, errorListener);
    mListener = listener;
    this.mParams = params;
    mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    buildMultipartEntity(file);
  }

  public MultipartRequest(String url, Response.Listener<JSONObject> listener,
      Response.ErrorListener errorListener, Bitmap bitmap, Map<String, String> params) {
    super(Method.POST, url, errorListener);
    mListener = listener;
    this.mParams = params;
    mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    buildMultipartEntity(bitmap);
  }

  private void buildMultipartEntity(File file) {
    FileBody fileBody = new FileBody(file);
    if (fileBody.getContentLength() <= 0) {
      Log.w(TAG, "file: " + file.getAbsolutePath() + " not exists!");
      return;
    }
    mBuilder.addPart(FILE_PART_NAME, fileBody);
    for (Map.Entry<String, String> entry : mParams.entrySet()) {
      mBuilder.addTextBody(entry.getKey(), entry.getValue());
    }
    mEntity = mBuilder.build();
  }

  private void buildMultipartEntity(Bitmap bitmap) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    bitmap.compress(CompressFormat.PNG, 100, bos);
    byte[] b = bos.toByteArray();
    mBuilder.addBinaryBody(FILE_PART_NAME, b, ContentType.create("image/x-png"), "avatar_" + System.nanoTime() + ".png");
    for (Map.Entry<String, String> entry : mParams.entrySet()) {
      mBuilder.addTextBody(entry.getKey(), entry.getValue());
    }
    mEntity = mBuilder.build();
  }

  @Override
  public String getBodyContentType() {
    return mEntity.getContentType().getValue();
  }

  @Override
  public byte[] getBody() throws AuthFailureError {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      mEntity.writeTo(bos);
    } catch (IOException e) {
      VolleyLog.e("IOException writing to ByteArrayOutputStream");
    }
    return bos.toByteArray();
  }

  @Override
  protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
    try {
      String jsonString =
          new String(response.data, HttpHeaderParser.parseCharset(response.headers));
      JSONObject jsonObject = new JSONObject(jsonString);
      return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
    } catch (UnsupportedEncodingException e) {
      return Response.error(new ParseError(e));
    } catch (JSONException je) {
      return Response.error(new ParseError(je));
    }
  }

  @Override
  protected void deliverResponse(JSONObject response) {
    mListener.onResponse(response);
  }
}
