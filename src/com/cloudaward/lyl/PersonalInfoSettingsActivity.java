package com.cloudaward.lyl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudaward.lyl.consts.AppPrefsConsts;
import com.cloudaward.lyl.utils.ActionBarUtils;
import com.cloudaward.lyl.utils.ImageUtils;
import com.cloudaward.lyl.utils.SharedPreferencesUtils;


public class PersonalInfoSettingsActivity extends AbstractCheckLoginActivity implements OnClickListener {
  
  private RelativeLayout mAvatarLayout;
  private ImageView mAvatarImageView;
  
  private RelativeLayout mNicknameLayout;
  private TextView mNicknameTextView;
  
  private RelativeLayout mCellphoneLayout;
  private RelativeLayout mGenderLayout;
  private RelativeLayout mPersonaBaseInfoLayout;
  private RelativeLayout mThirdpartyAuthSettingsLayout;
  private RelativeLayout mMessagesSettingsLayout;
  private RelativeLayout mClearCacheLayout;
  private RelativeLayout mScoreLayout;
  
  private Button mLogoutButton;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_personal_info_settings);
    
    ActionBarUtils.initGeneralActionBar(this);
    
    mAvatarLayout = (RelativeLayout) findViewById(R.id.layout_avatar);
    mAvatarImageView = (ImageView) findViewById(R.id.iv_avatar);
    mAvatarLayout.setOnClickListener(this);
    
    mNicknameLayout = (RelativeLayout) findViewById(R.id.layout_nickname);
    mNicknameTextView = (TextView) findViewById(R.id.tv_nickname);
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
  protected void onStart() {
    super.onStart();
    SharedPreferences prefs = SharedPreferencesUtils.getPreferences(getApplicationContext(), AppPrefsConsts.PREFS_USER);
    String avatarWholeUrl = prefs.getString(AppPrefsConsts.PREFS_USER_KEY_AVATAR_WHOLE_URL, "");
    if (avatarWholeUrl != null) {
      ImageUtils.displayAvatar(mAvatarImageView, avatarWholeUrl);
    }
    mNicknameTextView.setText(prefs.getString(AppPrefsConsts.PREFS_USER_KEY_NICKNAME, ""));
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
      Intent intent = new Intent(this, CompleteBaseInfoActivity.class);
      startActivity(intent);
    }
    else if(v == mLogoutButton) {
      mSessionManager.logout();
    }
  }
}
