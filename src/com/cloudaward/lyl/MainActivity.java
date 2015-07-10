package com.cloudaward.lyl;

import java.util.Arrays;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.cloudaward.lyl.fragments.ComingFragment;
import com.cloudaward.lyl.fragments.FinalFragment;
import com.cloudaward.lyl.fragments.LatestFragment;


@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {

  private ListView mDrawerList;
  private DrawerLayout mDrawerLayout;
  private ArrayAdapter<String> mAdapter;
  private ActionBarDrawerToggle mDrawerToggle;

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

    mFragmentPageTitles = Arrays.asList(new String[] {
        getResources().getString(R.string.latest_online),
        getResources().getString(R.string.final_rushing),
        getResources().getString(R.string.coming_soon)
    });
    
    initDrawer();

    initViewPager();

  }

  private void initDrawer() {
    mDrawerList = (ListView) findViewById(R.id.navList);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    addDrawerItems();
    setupDrawer();
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
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
  }


  private void addDrawerItems() {
    String[] osArray = {"Android", "iOS", "Windows", "OS X", "Linux"};
    mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
    mDrawerList.setAdapter(mAdapter);

    mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void setupDrawer() {
    mDrawerToggle =
        new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

          /** Called when a drawer has settled in a completely open state. */
          public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
          }

          /** Called when a drawer has settled in a completely closed state. */
          public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
          }

        };

    mDrawerToggle.setDrawerIndicatorEnabled(true);
    mDrawerLayout.setDrawerListener(mDrawerToggle);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    // Sync the toggle state after onRestoreInstanceState has occurred.
    mDrawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
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

    // Activate the navigation drawer toggle
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
