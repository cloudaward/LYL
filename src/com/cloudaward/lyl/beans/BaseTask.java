package com.cloudaward.lyl.beans;


/**
 * Abstract task
 * 
 * @author Navy
 *
 */
public abstract class BaseTask {

  protected State state;

  public void setState(State state) {
    this.state = state;
  }

  public State getState() {
    return state;
  }

  public enum State {

    PREPARED(0),

    STARTED(1),

    PAUSED(2),

    FREEZED(3),

    STOPPED(4),

    UNKNOWN(-1);

    private int value;

    private State(int value) {
      this.value = value;
    }

    public void setValue(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public static State getState(int value) {
      switch (value) {
        case 0:
          return PREPARED;

        case 1:
          return STARTED;

        case 2:
          return PAUSED;

        case 3:
          return FREEZED;

        case 4:
          return STOPPED;

        default:
          return UNKNOWN;
      }
    }
  }

}
