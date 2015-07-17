package com.cloudaward.lyl.beans;

public class RegisterContext {

  private String cellphoneNumber;

  private String captcha;

  private String password;

  private String invitationCode;

  public String getCellphoneNumber() {
    return cellphoneNumber;
  }

  public void setCellphoneNumber(String cellphoneNumber) {
    this.cellphoneNumber = cellphoneNumber;
  }

  public String getCaptcha() {
    return captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getInvitationCode() {
    return invitationCode;
  }

  public void setInvitationCode(String invitationCode) {
    this.invitationCode = invitationCode;
  }

}
