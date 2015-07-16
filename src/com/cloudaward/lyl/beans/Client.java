package com.cloudaward.lyl.beans;

import android.content.Context;

import com.cloudaward.lyl.utils.SystemUtils;


public class Client {

  private String platform;

  private String uuid;

  private String osVersion;

  private String clientVersion;

  private String channel;

  private String screen;

  private String location;

  public Client(Context context) {
    this.platform = "2";
    this.uuid = SystemUtils.getUUID(context);
    this.osVersion = android.os.Build.VERSION.RELEASE;
    this.channel = "unknown";
    this.screen = SystemUtils.getScreenSize(context);
    this.location = "unknown";
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public String getClientVersion() {
    return clientVersion;
  }

  public void setClientVersion(String clientVersion) {
    this.clientVersion = clientVersion;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getScreen() {
    return screen;
  }

  public void setScreen(String screen) {
    this.screen = screen;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}
