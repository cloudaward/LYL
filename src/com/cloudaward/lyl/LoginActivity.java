package com.cloudaward.lyl;

import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cloudaward.lyl.beans.LoginContext;
import com.cloudaward.lyl.consts.UrlConsts;
import com.cloudaward.lyl.network.LylJSONObject;
import com.cloudaward.lyl.network.LylJsonObjectRequest;
import com.cloudaward.lyl.utils.ActivityUtils;
import com.cloudaward.lyl.utils.Des3;
import com.cloudaward.lyl.utils.MD5;
import com.cloudaward.lyl.utils.SharedPreferencesUtils;


@SuppressWarnings("deprecation")
public class LoginActivity extends ActionBarActivity {

  protected static final String TAG = "LoginActivity";

  private Button mLoginButton;

  private EditText mUsernameEditText;

  private EditText mPasswordEditText;

  private static SparseArray<String> errorMsg = new SparseArray<String>();

  static {
    errorMsg.put(1, "用户名不能为空");
    errorMsg.put(2, "密码不能为空");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    initActionBar();

    initLoginButton();

    initUsernameEditText();

    initPasswordEditText();
  }

  private void initActionBar() {
    ActivityUtils.initGeneralActionBar(this, getResources().getString(R.string.login));
  }

  private void initPasswordEditText() {
    mPasswordEditText = (EditText) findViewById(R.id.it_password);
    mPasswordEditText.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub

      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub

      }

      @Override
      public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

      }
    });
  }

  private void initUsernameEditText() {
    mUsernameEditText = (EditText) findViewById(R.id.it_username);
    SharedPreferences prefs = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
    String username = prefs.getString("username", "");
    mUsernameEditText.setText(username);
    mUsernameEditText.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

  }

  private void initLoginButton() {

    mLoginButton = (Button) findViewById(R.id.btn_login);
    mLoginButton.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        String username = mUsernameEditText.getText().toString();
        if (username == null || username.length() <= 0) {
          Toast.makeText(LoginActivity.this, errorMsg.get(1), Toast.LENGTH_SHORT).show();
          return;
        }
        String password = mPasswordEditText.getText().toString();
        if (password == null || username.length() <= 0) {
          Toast.makeText(LoginActivity.this, errorMsg.get(2), Toast.LENGTH_SHORT).show();
          return;
        }
        LoginContext context = new LoginContext();
        context.setAccount(Des3.encode(username));
        context.setPassword(MD5.md5(password));
        login(context);
      }
    });

  }

  protected void login(LoginContext loginContext) {
    LylJSONObject jsonRequest = null;
    try {
      JSONObject data = new JSONObject();
      data.put("account", URLEncoder.encode(loginContext.getAccount()));
      data.put("password", URLEncoder.encode(loginContext.getPassword()));
      jsonRequest = new LylJSONObject(this, data);
    } catch (JSONException e) {
      Log.e(TAG, e.getMessage());
      return;
    }
    // @formatter:off
    LylJsonObjectRequest request = new LylJsonObjectRequest(Method.POST, UrlConsts.loginUrl, jsonRequest,
        new Listener<JSONObject>() {
        
          @Override
          public void onResponse(JSONObject response) {
            int code = -1;
            try {
              code = response.getInt("code");
            } catch (JSONException e) {
              e.printStackTrace();
            }
            if(code == 0) {
              SharedPreferencesUtils.putString(LoginActivity.this, "username", mUsernameEditText.getText().toString());
              ActivityUtils.startActivity(LoginActivity.this, MainActivity.class);
            }
            if(code == 1){
              Toast.makeText(LoginActivity.this, getResources().getString(R.string.password_error), Toast.LENGTH_SHORT).show();
            }
            else if(code == 2) {
              Toast.makeText(LoginActivity.this, getResources().getString(R.string.username_not_found), Toast.LENGTH_SHORT).show();
            } 
          }
        }, 
        new ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError error) {
            Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, error.getMessage());
          }
        });
    MainApplication.getInstance().getRequestQueue().add(request);
    // @formatter:on
    Log.d(TAG, request.toString());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.login, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
