package com.cloudaward.lyl;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cloudaward.lyl.utils.ActionBarUtils;


@SuppressWarnings("deprecation")
public class PersonalInfoSettingsActivity extends ActionBarActivity implements OnClickListener {
  
  private RelativeLayout mAvatarLayout;
  private RelativeLayout mNicknameLayout;
  private RelativeLayout mCellphoneLayout;
  private RelativeLayout mGenderLayout;
  private RelativeLayout mPersonaBaseInfoLayout;
  private RelativeLayout mThirdpartyAuthSettingsLayout;
  private RelativeLayout mMessagesSettingsLayout;
  private RelativeLayout mClearCacheLayout;
  private RelativeLayout mScoreLayout;
  
  private Button mLogoutButton;
  
  private SessionManager mSessionManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_personal_info_settings);
    
    mSessionManager = new SessionManager(this);
    
    ActionBarUtils.initGeneralActionBar(this, getTitle());
    
    mAvatarLayout = (RelativeLayout) findViewById(R.id.layout_avatar);
    mAvatarLayout.setOnClickListener(this);
    
    mNicknameLayout = (RelativeLayout) findViewById(R.id.layout_nickname);
    mNicknameLayout.setOnClickListener(this);
    
    mCellphoneLayout = (RelativeLayout) findViewById(R.id.layout_cellphone);
    mCellphoneLayout.setOnClickListener(this);
    
    mGenderLayout = (RelativeLayout) findViewById(R.id.layout_gender);
    mGenderLayout.setOnClickListener(this);
    
    mPersonaBaseInfoLayout = (RelativeLayout) findViewById(R.id.layout_personal_base_info);
    mPersonaBaseInfoLayout.setOnClickListener(this);
    
    mThirdpartyAuthSettingsLayout = (RelativeLayout) findViewById(R.id.layout_thirdparty_auth_settings);
    mThirdpartyAuthSettingsLayout.setOnClickListener(this);
    
    mMessagesSettingsLayout = (RelativeLayout) findViewById(R.id.layout_messages_settings);
    mMessagesSettingsLayout.setOnClickListener(this);
    
    mClearCacheLayout = (RelativeLayout) findViewById(R.id.layout_clear_cache);
    mClearCacheLayout.setOnClickListener(this);

    mScoreLayout = (RelativeLayout) findViewById(R.id.layout_score);
    mScoreLayout.setOnClickListener(this);
    
    mLogoutButton = (Button) findViewById(R.id.btn_logout);
    mLogoutButton.setOnClickListener(this);
    
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.personal_settings, menu);
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
    if(v == mAvatarLayout) {
//      ActivityUtils.startActivity(this, toActivityClass);
    }
    else if(v == mLogoutButton) {
      mSessionManager.logout();
    }
  }
}
