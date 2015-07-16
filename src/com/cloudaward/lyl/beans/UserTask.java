package com.cloudaward.lyl.beans;

import java.util.Map;

public class UserTask {

  //@formatter:off
  public static final String ATTR_JOINEDTASKS = "joinedTasks";
  public static final String ATTR_PUBLISHEDTASKS = "publishedTasks";
  public static final String ATTR_TOTALAWARDMONEY = "totalAwardMoney";
  public static final String ATTR_BALANCEMONEY= "balanceMoney";
  
  public static final String[] ATTR_NAMES = new String[] {
    ATTR_JOINEDTASKS,
    ATTR_PUBLISHEDTASKS, 
    ATTR_TOTALAWARDMONEY,
    ATTR_BALANCEMONEY
  };
  //@formatter:on

  private int joinedTasks;

  private int publishedTasks;

  private int totalAwardMoney;

  private int balanceMoney;

  public int getJoinedTasks() {
    return joinedTasks;
  }

  public void setJoinedTasks(int joinedTasks) {
    this.joinedTasks = joinedTasks;
  }

  public int getPublishedTasks() {
    return publishedTasks;
  }

  public void setPublishedTasks(int publishedTasks) {
    this.publishedTasks = publishedTasks;
  }

  public int getTotalAwardMoney() {
    return totalAwardMoney;
  }

  public void setTotalAwardMoney(int totalAwardMoney) {
    this.totalAwardMoney = totalAwardMoney;
  }

  public int getBalanceMoney() {
    return balanceMoney;
  }

  public void setBalanceMoney(int balanceMoney) {
    this.balanceMoney = balanceMoney;
  }

  public Object getAttribute(String key) {
    // @formatter:off
    if(ATTR_JOINEDTASKS.equals(key)) {
      return getJoinedTasks();
    }
    else if(ATTR_PUBLISHEDTASKS.equals(key)) {
      return getPublishedTasks();
    }
    else if(ATTR_TOTALAWARDMONEY.equals(key)) {
      return getTotalAwardMoney();
    }
    else if(ATTR_BALANCEMONEY.equals(key)) {
      return getBalanceMoney();
    }
    // @formatter:on
    return null;
  }
  
  public static class UserTaskEntry<K, V> implements Map.Entry<K, V> {

    private final K key;
    private V value;
    
    public UserTaskEntry(K key) {
      this.key = key;
    }
    
    public UserTaskEntry(K key, V value) {
      this.key = key;
      this.value = value;
    }
    
    @Override
    public K getKey() {
      return key;
    }

    @Override
    public V getValue() {
      return value;
    }

    @Override
    public V setValue(V value) {
      final V oldValue = this.value;
      this.value = value;
      return oldValue;
    }
    
  }
  
}
