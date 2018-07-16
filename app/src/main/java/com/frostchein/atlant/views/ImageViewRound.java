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

package com.frostchein.atlant.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.frostchein.atlant.R;


public class ImageViewRound extends android.support.v7.widget.AppCompatImageView {

  private float radius = 9.0f;
  private int color = Color.TRANSPARENT;

  public ImageViewRound(Context context) {
    super(context);
  }

  public ImageViewRound(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageViewRound, 0, 0);
    try {
      radius = ta.getFloat(R.styleable.ImageViewRound_radius, radius);
      color = ta.getColor(R.styleable.ImageViewRound_color, color);
    } finally {
      ta.recycle();
    }
  }

  public void setColor(int color) {
    this.color = color;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(color);
    Path path = new Path();
    RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
    path.addRoundRect(rect, radius, radius, Path.Direction.CW);
    canvas.drawRoundRect(rect, radius, radius, paint);
    canvas.clipPath(path);
    super.onDraw(canvas);
  }
}