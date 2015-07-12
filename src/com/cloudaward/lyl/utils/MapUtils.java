package com.cloudaward.lyl.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapUtils {

  public static Map<String, String> toMap(Object object) {
    Map<String, String> map = new HashMap<String, String>();
    Class<?> clazz = object.getClass();
    Field[] fields = clazz.getDeclaredFields();
    List<String> fieldNames = new ArrayList<String>();
    for (Field field : fields) {
      fieldNames.add(field.getName());
    }
    Method[] methods = clazz.getDeclaredMethods();
    try {
      for (Method method : methods) {
        String name = method.getName();
        if (name.startsWith("get")) {
          String field = name.substring(name.indexOf("get") + "get".length(), name.length());
          field = field.substring(0, 1).toLowerCase(Locale.getDefault()) + field.substring(1);
          if (fieldNames.contains(field)) {
            Object retValue = method.invoke(object, new Object[] {});
            map.put(field, retValue != null && retValue.toString() != null ? retValue.toString() : "");
          }
        }
      }
    } catch (Exception e) {

    }
    return map;
  }

}
