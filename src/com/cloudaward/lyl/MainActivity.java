package com.cloudaward.lyl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.cloudaward.lyl.bean.NavMenuItem;
import com.cloudaward.lyl.fragments.ComingFragment;
import com.cloudaward.lyl.fragments.FinalFragment;
import com.cloudaward.lyl.fragments.LatestFragment;

/**
 * Main activity
 * 
 * @author Navy
 *
 */

public class MainActivity extends BaseActivity {

  private DrawerLayout mDrawerLayout;
  private ActionBarDrawerToggle mDrawerToggle;

  private ListView mNavMenuList;
  private List<NavMenuItem> mNavMenuItems;
  private NavMenuListAdapter mNavMenuListAdapter;

  private ListView mUserTaskTotalList;
  private List<Entry<String, String>> mUserTaskTotalItems;
  private UserTaskTotalListAdapter mUserTaskTotalListAdapter;
  
  private FragmentPagerAdapter mFragmentPagerAdapter;
  private ViewPager mViewPager;
  private com.astuetz.PagerSlidingTabStrip mPagerSlidingTabStrip;

  private List<String> mFragmentPageTitles;
  private Fragment mLatestFragment;
  private Fragment mFinalFragment;
  private Fragment mComingFragment;
  

  private TextView mSettings;
  private TextView mMessages;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mFragmentPageTitles =
        Arrays.asList(new String[] {
            getResources().getString(R.string.latest_online),
            getResources().getString(R.string.final_rushing),
            getResources().getString(R.string.coming_soon),
            });

    initDrawer();

    initViewPager();
    
    mSettings = (TextView) findViewById(R.id.tv_settings);
    mSettings.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        // TODO
        Toast.makeText(getApplicationContext(), mSettings.getText() + " clicked TODO!!!", Toast.LENGTH_SHORT).show();
        
      }
    });
    
    mMessages = (TextView) findViewById(R.id.tv_messages);
    mMessages.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        // TODO
        Toast.makeText(getApplicationContext(), mMessages.getText() + " clicked TODO!!!", Toast.LENGTH_SHORT).show();
      }
    });
    
  }

  private void initDrawer() {
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mNavMenuList = (ListView) findViewById(R.id.navList);
    mUserTaskTotalList = (ListView) findViewById(R.id.taskTotalList);
    initNavMenuItems();
    initUserTaskTotalItems();
    addNavMenuItems();
    addUserTotalTaskItems();
    setupDrawer();
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
  }

  private void addUserTotalTaskItems() {
    mUserTaskTotalListAdapter = new UserTaskTotalListAdapter(this, mUserTaskTotalItems);
    mUserTaskTotalList.setAdapter(mUserTaskTotalListAdapter);
    mUserTaskTotalList.setEnabled(false);
  }

  private void initUserTaskTotalItems() {
    String[] labels = getResources().getStringArray(R.array.userTaskTotalTexts);
    mUserTaskTotalItems = new ArrayList<Map.Entry<String,String>>();
    for (final String label : labels) {
      mUserTaskTotalItems.add(new Entry<String, String>() {
        @Override
        public String setValue(String object) {
          return "0";
        }
        
        @Override
        public String getValue() {
          return "0";
        }
        
        @Override
        public String getKey() {
          return label;
        }
      });
    }
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
  
  private void initNavMenuItems() {
    TypedArray icons = getResources().obtainTypedArray(R.array.navMenuItemIcons);
    String[] texts = getResources().getStringArray(R.array.navMenuItemTexts);
    int length = icons.length();
    mNavMenuItems = new ArrayList<NavMenuItem>(length);
    for (int i = 0; i < length; i++) {
      Drawable icon = icons.getDrawable(i);
      NavMenuItem item = new NavMenuItem(icon, texts[i]);
      mNavMenuItems.add(item);
    }
    icons.recycle();
  }


  private void addNavMenuItems() {
    mNavMenuListAdapter = new NavMenuListAdapter(this, mNavMenuItems);
    mNavMenuList.setAdapter(mNavMenuListAdapter);
    
    mNavMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final NavMenuItem menuItem = mNavMenuItems.get(position);
        mNavMenuListAdapter.notifyDataSetChanged();
        onNavItemClicked(menuItem);
      }
    });
  }

  protected void onNavItemClicked(NavMenuItem menuItem) {
    // TODO
    Toast.makeText(getApplicationContext(), menuItem.getText() + " menu clicked TODO!!!", Toast.LENGTH_SHORT).show();
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
  
  private static abstract class BaseListAdapter<T> extends BaseAdapter {

    protected Context context;
    
    protected List<T> items;
    
    public BaseListAdapter(Context context, List<T> items) {
      this.context = context;
      this.items = items;
    }
    
    @Override
    public int getCount() {
      return items.size();
    }

    @Override
    public Object getItem(int position) {
      return items.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }
  }
  
  private static class NavMenuListAdapter extends BaseListAdapter<NavMenuItem> {

    public NavMenuListAdapter(Context context, List<NavMenuItem> menuItems) {
      super(context, menuItems);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder = null;
      if(convertView == null) {
        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_item_nav_menu, parent, false);
        viewHolder.mIcon = (ImageView) convertView.findViewById(R.id.iv_menu_icon);
        viewHolder.mText = (TextView) convertView.findViewById(R.id.tv_menu_text);
        viewHolder.mFowardIcon = (ImageView) convertView.findViewById(R.id.iv_menu_forward);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }
      final NavMenuItem menuItem = items.get(position);
      viewHolder.mIcon.setImageDrawable(menuItem.getIcon());
      viewHolder.mText.setText(menuItem.getText());

      return convertView;
    }
    
    private static class ViewHolder {
      ImageView mIcon;
      TextView mText;
      @SuppressWarnings("unused")
      ImageView mFowardIcon;
    }
  }
  
  private static class UserTaskTotalListAdapter extends BaseListAdapter<Entry<String, String>> {

    public UserTaskTotalListAdapter(Context context, List<Entry<String, String>> userTaskTotal) {
      super(context, userTaskTotal);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder = null;
      if(convertView == null) {
        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_item_user_task_total, parent, false);
        viewHolder.mLabel = (TextView) convertView.findViewById(R.id.tv_label);
        viewHolder.mValue = (TextView) convertView.findViewById(R.id.tv_value);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }
      final Entry<String, String> item = items.get(position);
      viewHolder.mLabel.setText(item.getKey());
      viewHolder.mValue.setText(item.getValue());
      return convertView;
    }
    
    private static class ViewHolder {
      TextView mLabel;
      TextView mValue;
    }
  }
}
