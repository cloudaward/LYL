package com.cloudaward.lyl;

import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.cloudaward.lyl.fragments.ComingFragment;
import com.cloudaward.lyl.fragments.FinalFragment;
import com.cloudaward.lyl.fragments.LatestFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * Main activity
 * 
 * @author Navy
 *
 */

public class MainActivity extends BaseActivity implements OnClickListener {
  
  // The left side bar
  // @formatter:off
  private ResideMenu mResideMenu;
  private ResideMenuItem mPersonalInfoSettingsMenuItem;
  private ResideMenuItem mAccountMenuItem;
  private ResideMenuItem mJoinedTasksMenuItem;
  private ResideMenuItem mPublishedTasksMenuItem;
  private ResideMenuItem mInviteFriendsUseMenuItem;
  private ResideMenuItem mFeedbackMenuItem;
  private ResideMenuItem mAboutItem;
  // @formatter:on

  // The action bar
  // @formatter:off
  private ImageView mHomeImageView;
  private TextView mLocationTextView;
  private ImageView mAddImageView;
  // @formatter:on

  // The view pager and fragments
  // @formatter:off
  private LinearLayout mViewPagerLayout;
  private FragmentPagerAdapter mFragmentPagerAdapter;
  private ViewPager mViewPager;
  private com.astuetz.PagerSlidingTabStrip mPagerSlidingTabStrip;
  private List<String> mFragmentPageTitles;
  private Fragment mLatestFragment;
  private Fragment mFinalFragment;
  private Fragment mComingFragment;
  // @formatter:on

  private long exitTime = 0;
  private static final long quitInterval = 2000;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Initialize reside menu
    initResideMenu();

    // Initialize action bar
    initActionBar();

    // Initialize view pager& fragments
    initViewPager();
    
  }
  
  private void initActionBar() {
    // Hide action bar
    getSupportActionBar().hide();

    initHomeImageView();

    initLocationTextView();

    initAddImageView();
  }

  private void initLocationTextView() {
    mLocationTextView = (TextView) findViewById(R.id.tv_location);
    mLocationTextView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO call GPS

      }
    });
  }

  private void initAddImageView() {
    mAddImageView = (ImageView) findViewById(R.id.iv_add);
    mAddImageView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO
      }
    });
  }

  private void initHomeImageView() {
    mHomeImageView = (ImageView) findViewById(R.id.iv_home);
    mHomeImageView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        mResideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
      }
    });
  }

  @SuppressLint("InflateParams")
  private void initResideMenu() {
    mResideMenu = new ResideMenu(this);
    mResideMenu.setBackground(R.drawable.bg_residemenu);
    mResideMenu.attachToActivity(this);
    initResideMenuItems();

  }

  private void initResideMenuItems() {

    initPersonalInfoSettingsMenuItem();

    initAccountMenuItem();

    initJoinedTasksMenuItem();

    initPublishedTasksMenuItem();

    initInviteFriendsUseMenuItem();

    initFeedbackMenuItem();

    initAboutMenuItem();

  }

  private void initPersonalInfoSettingsMenuItem() {
    //@formatter:off
    mPersonalInfoSettingsMenuItem = new ResideMenuItem(this, 
        R.drawable.ic_personal_info_settings, 
        getResources().getString(R.string.title_activity_personal_info_settings),
        R.drawable.ic_forward
    );
    //@formatter:on
    mPersonalInfoSettingsMenuItem.setOnClickListener(this);
    mResideMenu.addMenuItem(mPersonalInfoSettingsMenuItem, ResideMenu.DIRECTION_LEFT);
  }

  private void initAccountMenuItem() {
    //@formatter:off
    mAccountMenuItem = new ResideMenuItem(this, 
        R.drawable.ic_my_account, 
        getResources().getString(R.string.title_activity_my_accout),
        R.drawable.ic_forward
        );
    //@formatter:on
    mAccountMenuItem.setOnClickListener(this);
    mResideMenu.addMenuItem(mAccountMenuItem, ResideMenu.DIRECTION_LEFT);
  }

  private void initJoinedTasksMenuItem() {
    //@formatter:off
    mJoinedTasksMenuItem = new ResideMenuItem(this, 
        R.drawable.ic_joined_tasks, 
        getResources().getString(R.string.title_activity_joined_tasks),
        R.drawable.ic_forward
        );
    //@formatter:on
    mJoinedTasksMenuItem.setOnClickListener(this);
    mResideMenu.addMenuItem(mJoinedTasksMenuItem, ResideMenu.DIRECTION_LEFT);
  }

  private void initPublishedTasksMenuItem() {
    //@formatter:off
    mPublishedTasksMenuItem = new ResideMenuItem(this, 
        R.drawable.ic_joined_tasks, 
        getResources().getString(R.string.title_activity_published_tasks),
        R.drawable.ic_forward
        );
    //@formatter:on
    mPublishedTasksMenuItem.setOnClickListener(this);
    mResideMenu.addMenuItem(mPublishedTasksMenuItem, ResideMenu.DIRECTION_LEFT);
  }

  private void initInviteFriendsUseMenuItem() {
    //@formatter:off
    mInviteFriendsUseMenuItem = new ResideMenuItem(this, 
        R.drawable.ic_invite_friends_use, 
        getResources().getString(R.string.title_activity_invite_friends_use),
        R.drawable.ic_forward
        );
    //@formatter:on
    mInviteFriendsUseMenuItem.setOnClickListener(this);
    mResideMenu.addMenuItem(mInviteFriendsUseMenuItem, ResideMenu.DIRECTION_LEFT);
  }

  private void initFeedbackMenuItem() {
    //@formatter:off
    mFeedbackMenuItem = new ResideMenuItem(this, 
        R.drawable.ic_feedback, 
        getResources().getString(R.string.title_activity_feedback),
        R.drawable.ic_forward
        );
    //@formatter:on
    mFeedbackMenuItem.setOnClickListener(this);
    mResideMenu.addMenuItem(mFeedbackMenuItem, ResideMenu.DIRECTION_LEFT);
  }

  private void initAboutMenuItem() {
    //@formatter:off
    mAboutItem = new ResideMenuItem(this, 
        R.drawable.ic_about, 
        getResources().getString(R.string.title_activity_about),
        R.drawable.ic_forward
        );
    //@formatter:on
    mAboutItem.setOnClickListener(this);
    mResideMenu.addMenuItem(mAboutItem, ResideMenu.DIRECTION_LEFT);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return mResideMenu.dispatchTouchEvent(ev);
  }

  private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public MyFragmentPagerAdapter(final FragmentManager fragmentManager) {
      super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          if (mLatestFragment == null) {
            mLatestFragment = LatestFragment.getInstance();
          }
          return mLatestFragment;
        case 1:
          if (mFinalFragment == null) {
            mFinalFragment = FinalFragment.getInstance();
          }
          return mFinalFragment;
        case 2:
          if (mComingFragment == null) {
            mComingFragment = ComingFragment.getInstance();
          }
          return mComingFragment;
        default:
          return null;
      }
    }

    @Override
    public int getCount() {
      return mFragmentPageTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mFragmentPageTitles.get(position);
    }

  }

  private void initViewPager() {
    // @formatter:off
    mFragmentPageTitles = Arrays.asList(new String[] {
        getResources().getString(R.string.latest_online),
        getResources().getString(R.string.final_rushing),
        getResources().getString(R.string.coming_soon),
    });
    // @formatter:on
    mViewPagerLayout = (LinearLayout) findViewById(R.id.layout_view_pager);
    mViewPager = (ViewPager) findViewById(R.id.viewPager);
    mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(mFragmentPagerAdapter);

    mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pagerSlidingTabStrip);
    mPagerSlidingTabStrip.setViewPager(mViewPager);

    mResideMenu.addIgnoredView(mViewPagerLayout);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    // noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onClick(View v) {
    // @formatter:off
    if (v == mPersonalInfoSettingsMenuItem) {
      Intent intent = new Intent();
      intent.setClass(this, PersonalInfoSettingsActivity.class);
      startActivity(intent);
    } 
    else if (v == mAccountMenuItem) {
      Intent intent = new Intent();
      intent.setClass(this, MyAccoutActivity.class);
      startActivity(intent);
    }
    else if (v == mJoinedTasksMenuItem) {
      Intent intent = new Intent();
      intent.setClass(this, JoinedTasksActivity.class);
      startActivity(intent);
    }
    else if (v == mPublishedTasksMenuItem) {
      Intent intent = new Intent();
      intent.setClass(this, PublishedTasksActivity.class);
      startActivity(intent);
    }
    else if (v == mInviteFriendsUseMenuItem) {
      Intent intent = new Intent();
      intent.setClass(this, InviteFriendsUseActivity.class);
      startActivity(intent);
    }
    else if (v == mFeedbackMenuItem) {
      Intent intent = new Intent();
      intent.setClass(this, FeedbackActivity.class);
      startActivity(intent);
    }
    else if (v == mAboutItem) {
      Intent intent = new Intent();
      intent.setClass(this, AboutActivity.class);
      startActivity(intent);
    }
    // @formatter:on
  }
  
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
      if ((System.currentTimeMillis() - exitTime) > quitInterval) {
        String message = getResources().getString(R.string.back_pressed_again_quit);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        exitTime = System.currentTimeMillis();
      } else {
        finish();
        System.exit(0);
      }
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

}
