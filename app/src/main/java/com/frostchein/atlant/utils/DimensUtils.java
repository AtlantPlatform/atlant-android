package com.frostchein.atlant.utils;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.util.TypedValue;

public class DimensUtils {

  public static float getFloatFromResources(Context context, @DimenRes int dimenRes) {
    TypedValue tempVal = new TypedValue();
    context.getResources().getValue(dimenRes, tempVal, true);
    return tempVal.getFloat();
  }
}
