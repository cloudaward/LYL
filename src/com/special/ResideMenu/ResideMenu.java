package com.special.ResideMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudaward.lyl.R;
import com.cloudaward.lyl.SessionManager;
import com.cloudaward.lyl.beans.UserTask;
import com.cloudaward.lyl.beans.UserTask.UserTaskEntry;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * User: special Date: 13-12-10 Time: 下午10:44 Mail: specialcyci@gmail.com
 */
public class ResideMenu extends FrameLayout {

  public static final int DIRECTION_LEFT = 0;
  public static final int DIRECTION_RIGHT = 1;
  private static final int PRESSED_MOVE_HORIZONTAL = 2;
  private static final int PRESSED_DOWN = 3;
  private static final int PRESSED_DONE = 4;
  private static final int PRESSED_MOVE_VERTICAL = 5;

  private ImageView imageViewShadow;
  private ImageView imageViewBackground;
  private ViewGroup layoutLeftMenu;
  private ViewGroup layoutRightMenu;
  private ViewGroup scrollViewLeftMenu;
  private ViewGroup scrollViewRightMenu;
  private ViewGroup scrollViewMenu;
  /** Current attaching activity. */
  private Activity activity;
  /** The DecorView of current activity. */
  private ViewGroup viewDecor;
  private TouchDisableView viewActivity;
  /** The flag of menu opening status. */
  private boolean isOpened;
  private float shadowAdjustScaleX;
  private float shadowAdjustScaleY;
  /** Views which need stop to intercept touch events. */
  private List<View> ignoredViews;
  private List<ResideMenuItem> leftMenuItems;
  private List<ResideMenuItem> rightMenuItems;
  private DisplayMetrics displayMetrics = new DisplayMetrics();
  private OnMenuListener menuListener;
  private float lastRawX;
  private boolean isInIgnoredView = false;
  private int scaleDirection = DIRECTION_LEFT;
  private int pressedState = PRESSED_DOWN;
  private List<Integer> disabledSwipeDirection = new ArrayList<Integer>();
  // Valid scale factor is between 0.0f and 1.0f.
  private float mScaleValue = 0.5f;

  private LinearLayout mTopLayout;
  private ListView mUserTaskList;
  private ListAdapter mUserTaskListAdapter;

  private TextView mSettingsTextView;
  private TextView mMessagesTextView;
  
  private SessionManager mSessionManager;

  public ResideMenu(Context context) {
    super(context);
    mSessionManager = new SessionManager(context);
    initViews(context);
  }

  private void initViews(Context context) {
    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.residemenu, this);
    scrollViewLeftMenu = (ViewGroup) findViewById(R.id.sv_left_menu);
    LayoutParams params =
        new LayoutParams(scrollViewLeftMenu.getLayoutParams().width, LayoutParams.MATCH_PARENT);
    scrollViewLeftMenu.setLayoutParams(params);
    scrollViewRightMenu = (ViewGroup) findViewById(R.id.sv_right_menu);
    imageViewShadow = (ImageView) findViewById(R.id.iv_shadow);
    layoutLeftMenu = (ViewGroup) findViewById(R.id.layout_left_menu);
    layoutRightMenu = (ViewGroup) findViewById(R.id.layout_right_menu);
    imageViewBackground = (ImageView) findViewById(R.id.iv_background);
    mTopLayout = (LinearLayout) findViewById(R.id.layout_top);

    initUserTaskList();

    initSettingsTextView();

    initMessagesTextView();

    if(!mSessionManager.isLoggedin()) {
      mTopLayout.setVisibility(GONE);
    }
  }

  private void initUserTaskList() {
    mUserTaskList = (ListView) findViewById(R.id.taskTotalList);
    // TODO query from DB by user id
    UserTask userTask = new UserTask();
    String[] labels = getResources().getStringArray(R.array.user_task_list_item_labels);
    String[] labelAlias = getResources().getStringArray(R.array.user_task_list_item_label_alias);
    List<UserTaskEntry<String, String>> items = new ArrayList<UserTask.UserTaskEntry<String, String>>();
    for (int i = 0; i < labels.length; i++) {
      String key = labels[i];
      String keyAlias = labelAlias[i];
      Object value = userTask.getAttribute(keyAlias);
      String strValue = value == null ? "0" : value.toString();
      UserTaskEntry<String, String> entry = new UserTaskEntry<String, String>(key, strValue);
      items.add(entry);
    }
    mUserTaskListAdapter = new ListAdapter(getContext(), items);
    mUserTaskList.setAdapter(mUserTaskListAdapter);
    mUserTaskList.setEnabled(false);
  }

  private static class ListAdapter extends BaseAdapter {

    final Context context;

    List<UserTaskEntry<String, String>> items;

    public ListAdapter(Context context, List<UserTaskEntry<String, String>> items) {
      this.context = context;
      this.items = items;
    }

    @Override
    public Object getItem(int position) {
      return items.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public int getCount() {
      return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder = null;
      if (convertView == null) {
        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_item_user_task, parent, false);
        viewHolder.mLabel = (TextView) convertView.findViewById(R.id.tv_label);
        viewHolder.mValue = (TextView) convertView.findViewById(R.id.tv_value);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }
      final Entry<String, String> item = items.get(position);
      viewHolder.mLabel.setText(item.getKey());
      // @formatter:off
      viewHolder.mValue.setText(item.getValue());
      // @formatter:on
      return convertView;
    }

    private static class ViewHolder {
      TextView mLabel;
      TextView mValue;
    }
  }

  private void initMessagesTextView() {
    mMessagesTextView = (TextView) findViewById(R.id.tv_messages);
    mMessagesTextView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO
        Toast.makeText(getContext(), mMessagesTextView.getText() + " clicked TODO!!!",
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initSettingsTextView() {
    mSettingsTextView = (TextView) findViewById(R.id.tv_settings);
    mSettingsTextView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO
        Toast.makeText(getContext(), mSettingsTextView.getText() + " clicked TODO!!!",
            Toast.LENGTH_SHORT).show();

      }
    });
  }

  @Override
  protected boolean fitSystemWindows(Rect insets) {
    // Applies the content insets to the view's padding, consuming that content (modifying the
    // insets to be 0),
    // and returning true. This behavior is off by default and can be enabled through
    // setFitsSystemWindows(boolean)
    // in api14+ devices.
    this.setPadding(viewActivity.getPaddingLeft() + insets.left, viewActivity.getPaddingTop()
        + insets.top, viewActivity.getPaddingRight() + insets.right,
        viewActivity.getPaddingBottom() + insets.bottom);
    insets.left = insets.top = insets.right = insets.bottom = 0;
    return true;
  }

  /**
   * Set up the activity;
   *
   * @param activity
   */
  public void attachToActivity(Activity activity) {
    initValue(activity);
    setShadowAdjustScaleXByOrientation();
    viewDecor.addView(this, 0);
  }

  private void initValue(Activity activity) {
    this.activity = activity;
    leftMenuItems = new ArrayList<ResideMenuItem>();
    rightMenuItems = new ArrayList<ResideMenuItem>();
    ignoredViews = new ArrayList<View>();
    viewDecor = (ViewGroup) activity.getWindow().getDecorView();
    viewActivity = new TouchDisableView(this.activity);

    View mContent = viewDecor.getChildAt(0);
    viewDecor.removeViewAt(0);
    viewActivity.setContent(mContent);
    addView(viewActivity);

    ViewGroup parent = (ViewGroup) scrollViewLeftMenu.getParent();
    parent.removeView(scrollViewLeftMenu);
    parent.removeView(scrollViewRightMenu);
  }

  private void setShadowAdjustScaleXByOrientation() {
    int orientation = getResources().getConfiguration().orientation;
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
      shadowAdjustScaleX = 0.034f;
      shadowAdjustScaleY = 0.12f;
    } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
      shadowAdjustScaleX = 0.06f;
      shadowAdjustScaleY = 0.07f;
    }
  }

  /**
   * Set the background image of menu;
   *
   * @param imageResource
   */
  public void setBackground(int imageResource) {
    imageViewBackground.setImageResource(imageResource);
  }

  /**
   * The visibility of the shadow under the activity;
   *
   * @param isVisible
   */
  public void setShadowVisible(boolean isVisible) {
    if (isVisible)
      imageViewShadow.setBackgroundResource(R.drawable.shadow_residemenu);
    else
      imageViewShadow.setBackgroundResource(0);
  }

  /**
   * Add a single item to the left menu;
   *
   * WARNING: It will be removed from v2.0.
   * 
   * @param menuItem
   */
  @Deprecated
  public void addMenuItem(ResideMenuItem menuItem) {
    this.leftMenuItems.add(menuItem);
    layoutLeftMenu.addView(menuItem);
  }

  /**
   * Add a single items;
   *
   * @param menuItem
   * @param direction
   */
  public void addMenuItem(ResideMenuItem menuItem, int direction) {
    if (direction == DIRECTION_LEFT) {
      this.leftMenuItems.add(menuItem);
      layoutLeftMenu.addView(menuItem);
    } else {
      this.rightMenuItems.add(menuItem);
      layoutRightMenu.addView(menuItem);
    }
  }

  /**
   * WARNING: It will be removed from v2.0.
   * 
   * @param menuItems
   */
  @Deprecated
  public void setMenuItems(List<ResideMenuItem> menuItems) {
    this.leftMenuItems = menuItems;
    rebuildMenu();
  }

  /**
   * Set menu items by a array;
   *
   * @param menuItems
   * @param direction
   */
  public void setMenuItems(List<ResideMenuItem> menuItems, int direction) {
    if (direction == DIRECTION_LEFT)
      this.leftMenuItems = menuItems;
    else
      this.rightMenuItems = menuItems;
    rebuildMenu();
  }

  private void rebuildMenu() {
    layoutLeftMenu.removeAllViews();
    layoutRightMenu.removeAllViews();
    for (ResideMenuItem leftMenuItem : leftMenuItems)
      layoutLeftMenu.addView(leftMenuItem);
    for (ResideMenuItem rightMenuItem : rightMenuItems)
      layoutRightMenu.addView(rightMenuItem);
  }

  /**
   * WARNING: It will be removed from v2.0.
   * 
   * @return
   */
  @Deprecated
  public List<ResideMenuItem> getMenuItems() {
    return leftMenuItems;
  }

  /**
   * Return instances of menu items;
   *
   * @return
   */
  public List<ResideMenuItem> getMenuItems(int direction) {
    if (direction == DIRECTION_LEFT)
      return leftMenuItems;
    else
      return rightMenuItems;
  }

  /**
   * If you need to do something on closing or opening menu, set a listener here.
   *
   * @return
   */
  public void setMenuListener(OnMenuListener menuListener) {
    this.menuListener = menuListener;
  }


  public OnMenuListener getMenuListener() {
    return menuListener;
  }

  /**
   * Show the menu;
   */
  public void openMenu(int direction) {

    setScaleDirection(direction);

    isOpened = true;
    AnimatorSet scaleDown_activity =
        buildScaleDownAnimation(viewActivity, mScaleValue, mScaleValue);
    AnimatorSet scaleDown_shadow =
        buildScaleDownAnimation(imageViewShadow, mScaleValue + shadowAdjustScaleX, mScaleValue
            + shadowAdjustScaleY);
    AnimatorSet alpha_menu = buildMenuAnimation(scrollViewMenu, 1.0f);
    scaleDown_shadow.addListener(animationListener);
    scaleDown_activity.playTogether(scaleDown_shadow);
    scaleDown_activity.playTogether(alpha_menu);
    scaleDown_activity.start();
  }

  /**
   * Close the menu;
   */
  public void closeMenu() {

    isOpened = false;
    AnimatorSet scaleUp_activity = buildScaleUpAnimation(viewActivity, 1.0f, 1.0f);
    AnimatorSet scaleUp_shadow = buildScaleUpAnimation(imageViewShadow, 1.0f, 1.0f);
    AnimatorSet alpha_menu = buildMenuAnimation(scrollViewMenu, 0.0f);
    scaleUp_activity.addListener(animationListener);
    scaleUp_activity.playTogether(scaleUp_shadow);
    scaleUp_activity.playTogether(alpha_menu);
    scaleUp_activity.start();
  }

  @Deprecated
  public void setDirectionDisable(int direction) {
    disabledSwipeDirection.add(direction);
  }

  public void setSwipeDirectionDisable(int direction) {
    disabledSwipeDirection.add(direction);
  }

  private boolean isInDisableDirection(int direction) {
    return disabledSwipeDirection.contains(direction);
  }

  private void setScaleDirection(int direction) {

    int screenWidth = getScreenWidth();
    float pivotX;
    float pivotY = getScreenHeight() * 0.5f;

    if (direction == DIRECTION_LEFT) {
      scrollViewMenu = scrollViewLeftMenu;
      pivotX = screenWidth * 1.5f;
    } else {
      scrollViewMenu = scrollViewRightMenu;
      pivotX = screenWidth * -0.5f;
    }

    ViewHelper.setPivotX(viewActivity, pivotX);
    ViewHelper.setPivotY(viewActivity, pivotY);
    ViewHelper.setPivotX(imageViewShadow, pivotX);
    ViewHelper.setPivotY(imageViewShadow, pivotY);
    scaleDirection = direction;
  }

  /**
   * return the flag of menu status;
   *
   * @return
   */
  public boolean isOpened() {
    return isOpened;
  }

  private OnClickListener viewActivityOnClickListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      if (isOpened())
        closeMenu();
    }
  };

  private Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
    @Override
    public void onAnimationStart(Animator animation) {
      if (isOpened()) {
        showScrollViewMenu(scrollViewMenu);
        if (menuListener != null)
          menuListener.openMenu();
      }
    }

    @Override
    public void onAnimationEnd(Animator animation) {
      // reset the view;
      if (isOpened()) {
        viewActivity.setTouchDisable(true);
        viewActivity.setOnClickListener(viewActivityOnClickListener);
      } else {
        viewActivity.setTouchDisable(false);
        viewActivity.setOnClickListener(null);
        hideScrollViewMenu(scrollViewLeftMenu);
        hideScrollViewMenu(scrollViewRightMenu);
        if (menuListener != null)
          menuListener.closeMenu();
      }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
  };

  /**
   * A helper method to build scale down animation;
   *
   * @param target
   * @param targetScaleX
   * @param targetScaleY
   * @return
   */
  private AnimatorSet buildScaleDownAnimation(View target, float targetScaleX, float targetScaleY) {

    AnimatorSet scaleDown = new AnimatorSet();
    scaleDown.playTogether(ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
        ObjectAnimator.ofFloat(target, "scaleY", targetScaleY));

    scaleDown.setInterpolator(AnimationUtils.loadInterpolator(activity,
        android.R.anim.decelerate_interpolator));
    scaleDown.setDuration(250);
    return scaleDown;
  }

  /**
   * A helper method to build scale up animation;
   *
   * @param target
   * @param targetScaleX
   * @param targetScaleY
   * @return
   */
  private AnimatorSet buildScaleUpAnimation(View target, float targetScaleX, float targetScaleY) {

    AnimatorSet scaleUp = new AnimatorSet();
    scaleUp.playTogether(ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
        ObjectAnimator.ofFloat(target, "scaleY", targetScaleY));

    scaleUp.setDuration(250);
    return scaleUp;
  }

  private AnimatorSet buildMenuAnimation(View target, float alpha) {

    AnimatorSet alphaAnimation = new AnimatorSet();
    alphaAnimation.playTogether(ObjectAnimator.ofFloat(target, "alpha", alpha));

    alphaAnimation.setDuration(250);
    return alphaAnimation;
  }

  /**
   * If there were some view you don't want reside menu to intercept their touch event, you could
   * add it to ignored views.
   *
   * @param v
   */
  public void addIgnoredView(View v) {
    ignoredViews.add(v);
  }

  /**
   * Remove a view from ignored views;
   * 
   * @param v
   */
  public void removeIgnoredView(View v) {
    ignoredViews.remove(v);
  }

  /**
   * Clear the ignored view list;
   */
  public void clearIgnoredViewList() {
    ignoredViews.clear();
  }

  /**
   * If the motion event was relative to the view which in ignored view list,return true;
   *
   * @param ev
   * @return
   */
  private boolean isInIgnoredView(MotionEvent ev) {
    Rect rect = new Rect();
    for (View v : ignoredViews) {
      v.getGlobalVisibleRect(rect);
      if (rect.contains((int) ev.getX(), (int) ev.getY()))
        return true;
    }
    return false;
  }

  private void setScaleDirectionByRawX(float currentRawX) {
    if (currentRawX < lastRawX)
      setScaleDirection(DIRECTION_RIGHT);
    else
      setScaleDirection(DIRECTION_LEFT);
  }

  private float getTargetScale(float currentRawX) {
    float scaleFloatX = ((currentRawX - lastRawX) / getScreenWidth()) * 0.75f;
    scaleFloatX = scaleDirection == DIRECTION_RIGHT ? -scaleFloatX : scaleFloatX;

    float targetScale = ViewHelper.getScaleX(viewActivity) - scaleFloatX;
    targetScale = targetScale > 1.0f ? 1.0f : targetScale;
    targetScale = targetScale < 0.5f ? 0.5f : targetScale;
    return targetScale;
  }

  private float lastActionDownX, lastActionDownY;

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    float currentActivityScaleX = ViewHelper.getScaleX(viewActivity);
    if (currentActivityScaleX == 1.0f)
      setScaleDirectionByRawX(ev.getRawX());

    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        lastActionDownX = ev.getX();
        lastActionDownY = ev.getY();
        isInIgnoredView = isInIgnoredView(ev) && !isOpened();
        pressedState = PRESSED_DOWN;
        break;

      case MotionEvent.ACTION_MOVE:
        if (isInIgnoredView || isInDisableDirection(scaleDirection))
          break;

        if (pressedState != PRESSED_DOWN && pressedState != PRESSED_MOVE_HORIZONTAL)
          break;

        int xOffset = (int) (ev.getX() - lastActionDownX);
        int yOffset = (int) (ev.getY() - lastActionDownY);

        if (pressedState == PRESSED_DOWN) {
          if (yOffset > 25 || yOffset < -25) {
            pressedState = PRESSED_MOVE_VERTICAL;
            break;
          }
          if (xOffset < -50 || xOffset > 50) {
            pressedState = PRESSED_MOVE_HORIZONTAL;
            ev.setAction(MotionEvent.ACTION_CANCEL);
          }
        } else if (pressedState == PRESSED_MOVE_HORIZONTAL) {
          if (currentActivityScaleX < 0.95)
            showScrollViewMenu(scrollViewMenu);

          float targetScale = getTargetScale(ev.getRawX());
          ViewHelper.setScaleX(viewActivity, targetScale);
          ViewHelper.setScaleY(viewActivity, targetScale);
          ViewHelper.setScaleX(imageViewShadow, targetScale + shadowAdjustScaleX);
          ViewHelper.setScaleY(imageViewShadow, targetScale + shadowAdjustScaleY);
          ViewHelper.setAlpha(scrollViewMenu, (1 - targetScale) * 2.0f);

          lastRawX = ev.getRawX();
          return true;
        }

        break;

      case MotionEvent.ACTION_UP:

        if (isInIgnoredView)
          break;
        if (pressedState != PRESSED_MOVE_HORIZONTAL)
          break;

        pressedState = PRESSED_DONE;
        if (isOpened()) {
          if (currentActivityScaleX > 0.56f)
            closeMenu();
          else
            openMenu(scaleDirection);
        } else {
          if (currentActivityScaleX < 0.94f) {
            openMenu(scaleDirection);
          } else {
            closeMenu();
          }
        }

        break;

    }
    lastRawX = ev.getRawX();
    return super.dispatchTouchEvent(ev);
  }

  public int getScreenHeight() {
    activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.heightPixels;
  }

  public int getScreenWidth() {
    activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.widthPixels;
  }

  public void setScaleValue(float scaleValue) {
    this.mScaleValue = scaleValue;
  }

  public interface OnMenuListener {

    /**
     * This method will be called at the finished time of opening menu animations.
     */
    public void openMenu();

    /**
     * This method will be called at the finished time of closing menu animations.
     */
    public void closeMenu();
  }

  private void showScrollViewMenu(ViewGroup scrollViewMenu) {
    if (scrollViewMenu != null && scrollViewMenu.getParent() == null) {
      addView(scrollViewMenu);
    }
  }

  private void hideScrollViewMenu(ViewGroup scrollViewMenu) {
    if (scrollViewMenu != null && scrollViewMenu.getParent() != null) {
      removeView(scrollViewMenu);
    }
  }
}
