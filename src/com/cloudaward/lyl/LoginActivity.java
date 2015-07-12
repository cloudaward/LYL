package com.cloudaward.lyl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

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
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cloudaward.lyl.bean.LoginContext;
import com.cloudaward.lyl.network.LylJsonObjectRequest;
import com.cloudaward.lyl.utils.Des3;
import com.cloudaward.lyl.utils.MD5;
import com.cloudaward.lyl.utils.MapUtils;


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

    initLoginButton();

    initUsernameEditText();
    
    initPasswordEditText();
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
          Toast.makeText(getApplicationContext(), errorMsg.get(1), Toast.LENGTH_SHORT).show();
          return;
        }
        String password = mPasswordEditText.getText().toString();
        if (password == null || username.length() <= 0) {
          Toast.makeText(getApplicationContext(), errorMsg.get(2), Toast.LENGTH_SHORT).show();
          return;
        }
        LoginContext context = new LoginContext();
        context.setAccount(Des3.encode(username));
        context.setPassword(MD5.md5(password));
        login(context);
      }
    });

  }

  protected void login(LoginContext context) {
    String url = "http://www.laoyinliang.com/user/login";
    Map<String, String> map = MapUtils.toMap(context);
    JSONObject jsonObject = new JSONObject(map);
    Map<String, String> dataMap = new HashMap<String, String>();
    dataMap.put("data", jsonObject.toString());
    JSONObject requestBody = new JSONObject(dataMap);
    Log.d(TAG, requestBody.toString());
    LylJsonObjectRequest request =
        new LylJsonObjectRequest(Method.POST, url, requestBody, new Listener<JSONObject>() {

          @Override
          public void onResponse(JSONObject response) {
            Log.d(TAG, response.toString());
          }
        }, new com.android.volley.Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError error) {
            Log.d(TAG, error.getMessage());
          }
        }, getApplicationContext());
    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    requestQueue.add(request);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.login, menu);
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
