package com.cloudaward.lyl.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class Des3 {

  private final static String iv = "01234567";

  private final static String encoding = "utf-8";

  private final static String DEFAULT_KEY = "1qaz@WSX3edc$RFV5tgb^YHN";

  public static String encode(String plainText) {
    return encode(plainText, DEFAULT_KEY);
  }

  /**
   * 3DES加密
   * 
   * @param plainText 普通文本
   * @return
   * @throws Exception
   */
  public static String encode(String plainText, String key) {
    if (key == null || key.length() <= 0) {
      return null;
    }
    byte[] encryptData;
    try {
      Key deskey = null;
      DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
      SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
      deskey = keyfactory.generateSecret(spec);

      Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
      IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
      encryptData = cipher.doFinal(plainText.getBytes(encoding));
    } catch (Exception e) {
      return null;
    }
    return Base64.encode(encryptData);
  }

  /**
   * Base64编码工具类
   */
  private static final class Base64 {
    
    private static final char[] legalChars =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    public static String encode(byte[] data) {
      int start = 0;
      int len = data.length;
      StringBuffer buf = new StringBuffer(data.length * 3 / 2);

      int end = len - 3;
      int i = start;
      int n = 0;

      while (i <= end) {
        int d =
            ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8)
                | (((int) data[i + 2]) & 0x0ff);

        buf.append(legalChars[(d >> 18) & 63]);
        buf.append(legalChars[(d >> 12) & 63]);
        buf.append(legalChars[(d >> 6) & 63]);
        buf.append(legalChars[d & 63]);

        i += 3;

        if (n++ >= 14) {
          n = 0;
          buf.append(" ");
        }
      }

      if (i == start + len - 2) {
        int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

        buf.append(legalChars[(d >> 18) & 63]);
        buf.append(legalChars[(d >> 12) & 63]);
        buf.append(legalChars[(d >> 6) & 63]);
        buf.append("=");
      } else if (i == start + len - 1) {
        int d = (((int) data[i]) & 0x0ff) << 16;

        buf.append(legalChars[(d >> 18) & 63]);
        buf.append(legalChars[(d >> 12) & 63]);
        buf.append("==");
      }
      return buf.toString();
    }
  }
  public static void main(String[] args) {
    String encode = Des3.encode("123");
    System.out.println(encode);
  }
}
