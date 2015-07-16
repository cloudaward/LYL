package com.cloudaward.lyl.network;

import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class LylJsonObjectRequest extends JsonObjectRequest {

  public LylJsonObjectRequest(String url, JSONObject jsonRequest, Listener<JSONObject> listener,
      ErrorListener errorListener) {
    super(url, jsonRequest, listener, errorListener);
  }

  public LylJsonObjectRequest(int method, String url, JSONObject jsonRequest,
      Listener<JSONObject> listener, ErrorListener errorListener) {
    super(method, url, jsonRequest, listener, errorListener);
  }
  
  @Override
  public Map<String, String> getHeaders() throws AuthFailureError {
    return super.getHeaders();
  }
  
}
