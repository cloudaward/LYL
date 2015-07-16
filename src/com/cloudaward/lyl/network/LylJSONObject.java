package com.cloudaward.lyl.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.cloudaward.lyl.beans.Client;
import com.cloudaward.lyl.utils.MapUtils;

public class LylJSONObject extends JSONObject {

  private static final String tag = LylJSONObject.class.getSimpleName();

  public LylJSONObject(final Context context, final JSONObject data) throws JSONException {
    super();
    JSONObject c = new JSONObject(MapUtils.toMap(new Client(context)));
    try {
      this.put("c", c);
    } catch (JSONException e) {
      Log.i(tag, e.getMessage());
    }
    this.put("data", data);
  }
}
