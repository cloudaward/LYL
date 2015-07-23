package com.cloudaward.lyl;

import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.cloudaward.lyl.beans.RegisterContext;
import com.cloudaward.lyl.consts.UrlConsts;
import com.cloudaward.lyl.network.LylJSONObject;
import com.cloudaward.lyl.network.LylJsonObjectRequest;
import com.cloudaward.lyl.utils.ActionBarUtils;
import com.cloudaward.lyl.utils.MD5;


@SuppressWarnings("deprecation")
public class SetAccoutPasswordActivity extends ActionBarActivity implements OnClickListener {

  private static final String TAG = SetAccoutPasswordActivity.class.getSimpleName();

  private EditText mCaptchaEditText;

  private Button mResendCaptchaButton;

  private EditText mPasswordEditText;

  private EditText mPasswordAgainEditText;

  private Button mNextStepButton;

  private CountDownTimer mCountDownTimer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set_accout_password);
    ActionBarUtils.initGeneralActionBar(this);

    mCaptchaEditText = (EditText) findViewById(R.id.et_input_captcha);

    mResendCaptchaButton = (Button) findViewById(R.id.btn_resend_captcha);

    mCountDownTimer = new CountDownTimer(60000, 1000) {

      @Override
      public void onTick(long millisUntilFinished) {
        mResendCaptchaButton.setText(getResources().getString(R.string.resend) + "("
            + millisUntilFinished / 1000 + ")");
        mResendCaptchaButton.setEnabled(false);
      }

      @Override
      public void onFinish() {
        mResendCaptchaButton.setText(getResources().getString(R.string.resend));
        mResendCaptchaButton.setEnabled(true);
      }
    };

    mPasswordEditText = (EditText) findViewById(R.id.et_input_password);

    mPasswordAgainEditText = (EditText) findViewById(R.id.et_input_password_again);

    mNextStepButton = (Button) findViewById(R.id.btn_next_step);
    mNextStepButton.setOnClickListener(this);

  }

  @Override
  protected void onStart() {
    super.onStart();
    mCountDownTimer.start();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.set_accout_password, menu);
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
  public void onClick(View v) {
    if (v == mNextStepButton) {
      RegisterContext context = new RegisterContext();
      // @formatter:off
      if (TextUtils.isEmpty(mCaptchaEditText.getText())) {
        Toast.makeText(getApplicationContext(),
            getResources().getString(R.string.captcha_not_null), Toast.LENGTH_SHORT).show();
        return;
      }
      if (TextUtils.isEmpty(mPasswordEditText.getText())) {
        Toast.makeText(getApplicationContext(),
            getResources().getString(R.string.password_not_null), Toast.LENGTH_SHORT).show();
        return;
      }
      if (TextUtils.isEmpty(mPasswordAgainEditText.getText())) {
        Toast.makeText(getApplicationContext(),
            getResources().getString(R.string.password_again_not_null), Toast.LENGTH_SHORT).show();
        return;
      }
      if (!mPasswordAgainEditText.getText().toString().equals(mPasswordEditText.getText().toString())) {
        Toast.makeText(getApplicationContext(),
            getResources().getString(R.string.password_not_consistency), Toast.LENGTH_SHORT).show();
        return;
      }
      if (getIntent() != null) {
        String phoneNumber = getIntent().getExtras().getString("phoneNumber", "");
        context.setCellphoneNumber(phoneNumber);
        context.setCaptcha(mCaptchaEditText.getText().toString());
        context.setPassword(URLEncoder.encode(MD5.md5(mPasswordEditText.getText().toString())));
        register(context);
      }
      // @formatter:on
    }
  }

  private void register(RegisterContext context) {
    LylJSONObject jsonRequest = null;
    JSONObject data = new JSONObject();
    try {
      data.put("mobile", context.getCellphoneNumber());
      data.put("validateCode", context.getCaptcha());
      data.put("password", context.getPassword());
      // TODO
      data.put("recommendAccount", "");
      jsonRequest = new LylJSONObject(this, data);
    } catch (JSONException e) {
      Log.e(TAG, e.getMessage());
      return;
    }
    // @formatter:off
    LylJsonObjectRequest request = new LylJsonObjectRequest(Method.POST, UrlConsts.registerUrl, jsonRequest, 
        new Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            Log.i(TAG, response.toString());
            int code = -1;
            try {
              code = response.getInt("code");
            } catch (JSONException e) {
              Log.e(TAG, e.getMessage());
            }
            if(code == 0) {
              updatePasswordSuccess();    
            } else {
              updatePasswordFail(response);
            }
          }
        },
        new ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.e(TAG, error.getMessage());
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });
    MainApplication.getInstance().getRequestQueue().add(request);
    // @formatter:on
  }
  
  private void updatePasswordFail(JSONObject response) {
    try {
      String message = response.getString("msg");
      Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    } catch (JSONException e) {
      Log.e(TAG, e.getMessage());
    }
  }

  private void updatePasswordSuccess() {
    Intent intent = new Intent();
    intent.setClass(this, CompleteBaseInfoActivity.class);
    startActivity(intent);
  }
}
