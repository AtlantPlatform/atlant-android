/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils;

import android.text.InputFilter;
import android.text.Spanned;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalDigitsInputFilter implements InputFilter {

  private Pattern mPattern;

  public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
    mPattern = Pattern.compile(
        "[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1)
            + "})?)||(\\.)?");
  }
  @Override
  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,      int dend) {
    Matcher matcher = mPattern.matcher(dest);
    if (!matcher.matches()) {
      return "";
    }
    return null;
  }

}
