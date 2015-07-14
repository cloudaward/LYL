package com.cloudaward.lyl;

import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.cloudaward.lyl.fragments.ComingFragment;
import com.cloudaward.lyl.fragments.FinalFragment;
import com.cloudaward.lyl.fragments.LatestFragment;
import com.cloudaward.lyl.utils.ActivityUtils;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * Main activity
 * 
 * @author Navy
 *
 */

public class MainActivity extends BaseActivity {


  private ResideMenu mResideMenu;

  private ImageView mHomeImageView;
  private TextView mLocationTextView;
  private ImageView mAddImageView;

  private FragmentPagerAdapter mFragmentPagerAdapter;
  private ViewPager mViewPager;
  private com.astuetz.PagerSlidingTabStrip mPagerSlidingTabStrip;

  private List<String> mFragmentPageTitles;
  private Fragment mLatestFragment;
  private Fragment mFinalFragment;
  private Fragment mComingFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initResideMenu();

    // Hide action bar
    getSupportActionBar().hide();

    initHomeImageView();

    initLocationTextView();

    initAddImageView();

    // @formatter:off
    mFragmentPageTitles = Arrays.asList(new String[] {
        getResources().getString(R.string.latest_online),
        getResources().getString(R.string.final_rushing),
        getResources().getString(R.string.coming_soon),
    });
    // @formatter:on
    // Initialize view pager& fragments
    initViewPager();
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
    mResideMenu.setBackground(R.drawable.residemenu_background);
    mResideMenu.attachToActivity(this);
    initResideMenuItems();

  }

  private void initResideMenuItems() {
    TypedArray icons = getResources().obtainTypedArray(R.array.navMenuItemIcons);
    String[] texts = getResources().getStringArray(R.array.navMenuItemTexts);
    int length = icons.length();
    for (int i = 0; i < length; i++) {
      int icon = icons.getResourceId(i, R.drawable.ic_launcher);
      ResideMenuItem item = new ResideMenuItem(getApplicationContext(), icon, texts[i], R.drawable.ic_forward);
      item.setOnClickListener(new ResideMenuItemOnClickedListener());
      mResideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT);
    }
    icons.recycle();
  }

  private final class ResideMenuItemOnClickedListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      ActivityUtils.startActivity(MainActivity.this, LoginActivity.class);
    }

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
    mViewPager = (ViewPager) findViewById(R.id.viewPager);
    mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(mFragmentPagerAdapter);

    mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pagerSlidingTabStrip);
    mPagerSlidingTabStrip.setViewPager(mViewPager);

    mResideMenu.addIgnoredView(mViewPager);
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

}
