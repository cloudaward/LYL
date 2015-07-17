package com.cloudaward.lyl;

import java.net.URLEncoder;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cloudaward.lyl.consts.UrlConsts;
import com.cloudaward.lyl.network.LylJSONObject;
import com.cloudaward.lyl.network.LylJsonObjectRequest;
import com.cloudaward.lyl.utils.ActionBarUtils;
import com.cloudaward.lyl.utils.Des3;


@SuppressWarnings("deprecation")
public class RegisterActivity extends ActionBarActivity implements OnClickListener {

  private static final String TAG = RegisterActivity.class.getSimpleName();

  private EditText mCellphoneZipcodeEditText;

  private EditText mCellphoneNumberEditText;

  private Button mNextStepButton;
  
  private CheckBox mAgreementCheckedTextView;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    ActionBarUtils.initGeneralActionBar(this);

    mCellphoneZipcodeEditText = (EditText) findViewById(R.id.et_cellphone_zipcode);

    mCellphoneNumberEditText = (EditText) findViewById(R.id.et_cellphone_number);

    mCellphoneNumberEditText.addTextChangedListener(new TextWatcher() {

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

    mAgreementCheckedTextView = (CheckBox) findViewById(R.id.cb_agreement);
    
    mNextStepButton = (Button) findViewById(R.id.btn_next_step);
    mNextStepButton.setOnClickListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.regist, menu);
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
      //@formatter:off
      if (TextUtils.isEmpty(mCellphoneNumberEditText.getText())) {
        Toast.makeText(this, getResources().getString(R.string.cellphone_number_not_null), Toast.LENGTH_SHORT).show();
        mCellphoneNumberEditText.requestFocus();
        return;
      }
      if (!mAgreementCheckedTextView.isChecked()) {
        Toast.makeText(this, getResources().getString(R.string.agreement_goon), Toast.LENGTH_SHORT).show();
        return;
      }
      String zipcode = mCellphoneZipcodeEditText.getText() != null ? mCellphoneZipcodeEditText.getText().toString() : "";
      //@formatter:on
      requestCaptcha(zipcode, mCellphoneNumberEditText.getText().toString());
    }
  }

  private void requestCaptcha(String zipcode, String cellphoneNumber) {
    LylJSONObject jsonRequest = null;
    final String encodedCellphoneNumber = URLEncoder.encode(Des3.encode(cellphoneNumber));
    try {
      JSONObject data = new JSONObject();
      data.put("type", 1);
      data.put("pn", encodedCellphoneNumber);
      jsonRequest = new LylJSONObject(this, data);
    } catch (JSONException e) {
      Log.e(TAG, e.getMessage());
    }
    // @formatter:off
    LylJsonObjectRequest request = new LylJsonObjectRequest(Method.POST, UrlConsts.sendCaptchaUrl, jsonRequest,
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
              requestCaptchaOK(encodedCellphoneNumber);
            }
          }

        }, 
        new ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.e(TAG, error.getMessage());
          }
        });
    // @formatter:on
    MainApplication.getInstance().getRequestQueue().add(request);
  }

  private void requestCaptchaOK(String encodedCellphoneNumber) {
    Intent intent = new Intent();
    Bundle bundle = new Bundle();
    bundle.putString("encodedCellphoneNumber", encodedCellphoneNumber);
    intent.putExtras(bundle);
    intent.setClass(RegisterActivity.this, SetAccoutPasswordActivity.class);
    startActivity(intent);
  }
}
