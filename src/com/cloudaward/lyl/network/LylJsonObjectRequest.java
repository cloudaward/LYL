package com.cloudaward.lyl.network;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class LylJsonObjectRequest extends JsonObjectRequest {

  private final Context context;

  private Map<String, String> requestHeader = new HashMap<String, String>();

  public LylJsonObjectRequest(int method, String url, JSONObject requestBody,
      Listener<JSONObject> listener, ErrorListener errorListener, Context context) {
    super(method, url, requestBody, listener, errorListener);
    this.context = context;
  }

  @Override
  public Map<String, String> getHeaders() throws AuthFailureError {
    return requestHeader;
  }

  public void setHeader(Map<String, String> map) {
    requestHeader.putAll(map);
  }
  
}
