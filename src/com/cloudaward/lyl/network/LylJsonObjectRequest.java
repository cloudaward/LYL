package com.cloudaward.lyl.network;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cloudaward.lyl.beans.ClientInfo;
import com.cloudaward.lyl.utils.MapUtils;
import com.cloudaward.lyl.utils.SystemUtils;

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
    ClientInfo clientInfo = new ClientInfo();
    clientInfo.setPlatform("2");
    clientInfo.setUuid(SystemUtils.getUUID(context));
    clientInfo.setOsVersion(android.os.Build.VERSION.RELEASE);
    // TODO
    clientInfo.setChannel("unknown");
    clientInfo.setScreen(SystemUtils.getScreenSize(context));
    clientInfo.setLocation("unknown");
    Map<String, String> map = MapUtils.toMap(clientInfo);
    JSONObject jsonObject = new JSONObject(map);
    requestHeader.put("c", jsonObject.toString());
    return requestHeader;
  }

  public void setHeader(Map<String, String> map) {
    requestHeader.putAll(map);
  }

}
