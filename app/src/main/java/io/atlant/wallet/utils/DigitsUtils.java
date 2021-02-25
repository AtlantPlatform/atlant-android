/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DigitsUtils {

  private static final String MASK = "0.########";
  public static final long divide = 1000000000000000000L;
  private static final int round = 18;

  private static String formatShow(BigDecimal value) {
    DecimalFormat percentFormat = new DecimalFormat(MASK);
    percentFormat.setDecimalSeparatorAlwaysShown(false);
    percentFormat.setRoundingMode(RoundingMode.DOWN);
    return percentFormat.format(value).replace(',', '.');
  }

  public static String valueToString(BigInteger value) {
    BigDecimal bigDecimal = new BigDecimal(value);
    return formatShow(bigDecimal.divide(BigDecimal.valueOf(divide), round, 0));
  }

  public static BigInteger getBase10from16(String s) {
    StringBuilder stringBuilder = new StringBuilder(s);
    if (stringBuilder.charAt(0) == '0' && stringBuilder.charAt(1) == 'x') {
      StringBuilder buffer = new StringBuilder();
      buffer.append(stringBuilder, 2, stringBuilder.length());
      return new BigInteger(buffer.toString(), 16);
    }
    return new BigInteger(stringBuilder.toString(), 16);
  }

  public static BigInteger getBase10FromString(String s) {
    return new BigInteger(s);
  }

}
