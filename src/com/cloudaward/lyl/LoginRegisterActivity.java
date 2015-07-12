package com.cloudaward.lyl;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudaward.lyl.utils.ActivityUtils;


@SuppressWarnings("deprecation")
public class LoginRegisterActivity extends ActionBarActivity {

  private ImageView mWeiboImgView;

  private ImageView mWeixinImgView;

  private Button mLoginButton;

  private Button mRegisterButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_register);

    // hide action bar
    getSupportActionBar().hide();

    initRegisterButton();

    initLoginButton();

    initWeiboImgView();

    initWeixinImgView();

  }

  private void initWeiboImgView() {

    mWeiboImgView = (ImageView) findViewById(R.id.iv_weibo);
    mWeiboImgView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "TOTO", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initWeixinImgView() {

    mWeixinImgView = (ImageView) findViewById(R.id.iv_weixin);
    mWeixinImgView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "TOTO", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initRegisterButton() {
    mRegisterButton = (Button) findViewById(R.id.btn_register);
    mRegisterButton.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        register();
      }

    });
  }

  private void initLoginButton() {
    mLoginButton = (Button) findViewById(R.id.btn_login);
    mLoginButton.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        login();
      }
    });
  }

  protected void register() {
//    ActivityUtils.startActivity(this, RegisterActivity.class);
    this.finish();
  }

  protected void login() {
    ActivityUtils.startActivity(this, LoginActivity.class);
    this.finish();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.login_register, menu);
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
