/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
 */

package com.frostchein.atlant.utils;

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
