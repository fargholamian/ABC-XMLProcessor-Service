package com.tradedoubler.xmlprocessorservice.utils;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

public class CSVWriter {

  public static void writeToCsv(List<?> objects, Writer writer) throws IOException {
    if (objects.isEmpty()) {
      writer.write("No data");
    }
    writer.write(getCsvHeader(objects.get(0)));

    objects.forEach(object -> {
      try {
        writer.write(getCsvForRow(object));
      } catch (IOException e) {
        throw new RuntimeException("Cannot write to the response" + e.getMessage());
      }
    });
  }

  private static String getCsvHeader(Object object) {
    StringBuilder stringBuilder = new StringBuilder();
    Class clazz = object.getClass();
    getHeadersFromClass(clazz, clazz.getPackage().getName(), "", stringBuilder);
    return stringBuilder + "\r\n";
  }

  private static String getCsvForRow(Object object) {
    StringBuilder stringBuilder = new StringBuilder();
    Class clazz = object.getClass();
    getValuesFromObject(clazz, clazz.getPackage().getName(), object, stringBuilder);
    return stringBuilder + "\r\n";
  }

  private static void getHeadersFromClass(Class c, String rootPackage, String parentName, StringBuilder stringBuilder) {
    Field[] fields = c.getDeclaredFields();
    for (Field field : fields) {
      Class filedClass = field.getType();
      String fieldName = field.getName();
      if (filedClass.getDeclaredFields().length > 0
          && filedClass.getPackage().getName().contains(rootPackage)
          && !filedClass.isEnum()) {
        getHeadersFromClass(filedClass, rootPackage, getCombinedName(parentName, fieldName), stringBuilder);
      } else {
        stringBuilder.append(",").append(getCombinedName(parentName, fieldName));
      }
    }
  }

  private static String getCombinedName(String parentName, String fieldName) {
    return "".equals(parentName) ? fieldName : parentName + "_" + fieldName;
  }

  private static void getValuesFromObject(Class c, String rootPackage, Object target, StringBuilder sb) {
    Field[] fields = c.getDeclaredFields();
    for (Field field : fields) {
      Class filedClass = field.getType();
      field.setAccessible(true);
      Object childObject = null;
      try {
        childObject = field.get(target);
      } catch (Exception ignored) {
      }
      if (filedClass.getDeclaredFields().length > 0
          && filedClass.getPackage().getName().contains(rootPackage)
          && !filedClass.isEnum()) {
        getValuesFromObject(filedClass, rootPackage, childObject, sb);
      } else {
        sb.append(",").append(String.valueOf(childObject).replaceAll(",", "").replaceAll("(\r\n|\n)", ""));
      }
    }
  }
}
