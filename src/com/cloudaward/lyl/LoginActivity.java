package com.cloudaward.lyl;

import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cloudaward.lyl.beans.LoginContext;
import com.cloudaward.lyl.consts.UrlConsts;
import com.cloudaward.lyl.network.LylJSONObject;
import com.cloudaward.lyl.network.LylJsonObjectRequest;
import com.cloudaward.lyl.utils.ActionBarUtils;
import com.cloudaward.lyl.utils.Des3;
import com.cloudaward.lyl.utils.MD5;


@SuppressWarnings("deprecation")
public class LoginActivity extends ActionBarActivity {

  protected static final String TAG = "LoginActivity";

  private Button mLoginButton;

  private EditText mUsernameEditText;

  private EditText mPasswordEditText;

  private TextView mRegisterTextView;

  private SessionManager mSessionManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    mSessionManager = new SessionManager(this);

    ActionBarUtils.initGeneralActionBar(this, new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    initLoginButton();

    initUsernameEditText();

    initPasswordEditText();

    initRegisterTextView();
  }

  private void initRegisterTextView() {
    mRegisterTextView = (TextView) findViewById(R.id.tv_register);
    mRegisterTextView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
      }
    });
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
    Map<String, String> map = mSessionManager.getLoginDetails();
    if (map != null) {
      String username = map.get(SessionManager.KEY_USERNAME);
      if (TextUtils.isEmpty(username)) {
        mUsernameEditText.setText(username);
      }
    }
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
        if (TextUtils.isEmpty(mUsernameEditText.getText())) {
          Toast.makeText(LoginActivity.this, getResources().getString(R.string.username_not_null), Toast.LENGTH_SHORT).show();
          return;
        }
        if (TextUtils.isEmpty(mPasswordEditText.getText())) {
          Toast.makeText(LoginActivity.this, getResources().getString(R.string.password_not_null), Toast.LENGTH_SHORT).show();
          return;
        }
        LoginContext context = new LoginContext();
        context.setAccount(Des3.encode(mUsernameEditText.getText().toString()));
        context.setPassword(MD5.md5(mPasswordEditText.getText().toString()));
        login(context);
      }
    });

  }

  private void login(LoginContext loginContext) {
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
              loginSuccess();
            } else {
              loginFail(code, response);
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

  private void loginSuccess() {
    mSessionManager.createLoginSession(mUsernameEditText.getText().toString());
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    finish();
  }

  private void loginFail(int errorCode, JSONObject response) {
    // @formatter:off
    try {
      String errorMsg = response.getString("msg");
      Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
      if(errorCode == 7) {
        Intent intent = new Intent(this, CompleteBaseInfoActivity.class);
        startActivity(intent);
      }
    } catch (JSONException e) {
      Log.e(TAG, e.getMessage());
    }
    // @formatter:on
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
  
  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    super.onBackPressed();
  }

}
