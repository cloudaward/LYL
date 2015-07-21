package com.cloudaward.lyl;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

@SuppressWarnings("deprecation")
public abstract class AbstractCheckLoginActivity extends ActionBarActivity {

  protected SessionManager mSessionManager;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mSessionManager = new SessionManager(this);
    mSessionManager.checkLogin();
  }
  
}
