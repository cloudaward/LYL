package com.cloudaward.lyl.beans;

import android.graphics.drawable.Drawable;

/**
 * Navigation menu item
 * 
 * @author Navy
 *
 */
public class NavMenuItem {

  private Drawable icon;

  private String text;

  public NavMenuItem() {
    super();
  }

  public NavMenuItem(String text) {
    super();
    this.text = text;
  }

  public NavMenuItem(Drawable icon, String text) {
    super();
    this.icon = icon;
    this.text = text;
  }



  public void setIcon(Drawable icon) {
    this.icon = icon;
  }

  public Drawable getIcon() {
    return icon;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

}
