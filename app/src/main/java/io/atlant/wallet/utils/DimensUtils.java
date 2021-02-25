/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.DimenRes;
import android.util.TypedValue;

public class DimensUtils {

  public static float getFloatFromResources(Context context, @DimenRes int dimenRes) {
    TypedValue tempVal = new TypedValue();
    context.getResources().getValue(dimenRes, tempVal, true);
    return tempVal.getFloat();
  }

  public static int dpToPx(Context context, float dp) {
    Resources r = context.getResources();
    return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
  }
}
