package com.frostchein.atlant.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontsUtils {

  public static void toOctarineBold(Context context, TextView text) {
    Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Octarine-Bold.otf");
    text.setTypeface(custom_font);
  }
}
