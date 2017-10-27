package com.frostchein.atlant.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.util.TypedValue;

public class DimensUtils {

  public static float getFloatFromResources(Context context, @DimenRes int dimenRes) {
    TypedValue tempVal = new TypedValue();
    context.getResources().getValue(dimenRes, tempVal, true);
    return tempVal.getFloat();
  }

  public static int dpToPx(Context context, int dp) {
    Resources r = context.getResources();
    return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
  }
}
