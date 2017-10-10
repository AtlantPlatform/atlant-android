package com.frostchein.atlant.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class DateUtils {

  private static DateTimeFormatter dateFormatFull = DateTimeFormat.forPattern("dd-MM-yyyy / HH:mm:ss");

  public static String getFormattedFullDate(long timestamp) {
    DateTime time = new DateTime(timestamp * 1000L);
    return dateFormatFull.print(time);
  }
}
