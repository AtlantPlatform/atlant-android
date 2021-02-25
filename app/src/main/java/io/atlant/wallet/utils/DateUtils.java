/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils;

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
