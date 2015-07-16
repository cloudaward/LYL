package com.cloudaward.lyl.utils;

import java.security.MessageDigest;
import java.util.Locale;

public class MD5 {

  private final static String PREFIX = "mima@lyl&ys!_";
  
  public final static String md5(String s) {
    s = PREFIX + s;
    try {
        byte[] btInput = s.getBytes();
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        mdInst.update(btInput);
        byte[] md = mdInst.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < md.length; i++) {
            int val = ((int) md[i]) & 0xff;
            if (val < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    } catch (Exception e) {
        return null;
    }
}

}
