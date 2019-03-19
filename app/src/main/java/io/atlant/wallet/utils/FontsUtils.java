/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontsUtils {

  public static void toOctarineBold(Context context, TextView text) {
    Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Octarine-Bold.otf");
    text.setTypeface(custom_font);
  }
}
