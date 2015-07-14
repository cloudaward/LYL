package com.cloudaward.lyl.beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Task bean
 * 
 * @author Navy
 *
 */
public class Task extends BaseTask implements Serializable {

  private static final long serialVersionUID = 1L;

  private int type = 0;

  private int id;

  private String logoUrl;

  private String title;

  private String description;

  private Location location;

  /**
   * Location(latitude, longitude)
   * 
   * @author Navy
   *
   */
  public class Location {

    double latitude;

    double longitude;

  }

  private Timestamp publishTime;

  private Timestamp startTime;

  private Timestamp endTime;

  private double awardCount;

  private double balanceCount;

  private double awardMoney;

  private double balanceMoney;

  private String[] imageUrls;

  private String address;

  private String[] contact;

  private String[] phones;

  private String website;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Timestamp getPublishTime() {
    return publishTime;
  }

  public void setPublishTime(Timestamp publishTime) {
    this.publishTime = publishTime;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  public double getAwardCount() {
    return awardCount;
  }

  public void setAwardCount(double awardCount) {
    this.awardCount = awardCount;
  }

  public double getBalanceCount() {
    return balanceCount;
  }

  public void setBalanceCount(double balanceCount) {
    this.balanceCount = balanceCount;
  }

  public double getAwardMoney() {
    return awardMoney;
  }

  public void setAwardMoney(double awardMoney) {
    this.awardMoney = awardMoney;
  }

  public double getBalanceMoney() {
    return balanceMoney;
  }

  public void setBalanceMoney(double balanceMoney) {
    this.balanceMoney = balanceMoney;
  }

  public String[] getImageUrls() {
    return imageUrls;
  }

  public void setImageUrls(String[] imageUrls) {
    this.imageUrls = imageUrls;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String[] getContact() {
    return contact;
  }

  public void setContact(String[] contact) {
    this.contact = contact;
  }

  public String[] getPhones() {
    return phones;
  }

  public void setPhones(String[] phones) {
    this.phones = phones;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

}
