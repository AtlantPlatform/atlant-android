package com.frostchein.atlant.utils;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import com.frostchein.atlant.R;

public class AnimationUtils {

  public static void copyBufferText(final Context context, final TextView textView) {
    textView.setEnabled(false);
    textView.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryText));
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
        textView.setEnabled(true);
      }
    }, 1000);
  }
}
