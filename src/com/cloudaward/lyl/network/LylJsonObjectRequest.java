package com.cloudaward.lyl.network;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cloudaward.lyl.MainApplication;

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
    Map<String, String> headers = super.getHeaders();

    if (headers == null || headers.equals(Collections.emptyMap())) {
      headers = new HashMap<String, String>();
    }

    MainApplication.getInstance().addSessionCookie(headers);

    return headers;
  }
  
  @Override
  protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
    MainApplication.getInstance().checkSessionCookie(response.headers);
    return super.parseNetworkResponse(response);
  }

}
