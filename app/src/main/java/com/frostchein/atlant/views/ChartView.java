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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.frostchein.atlant.utils.DimensUtils;
import java.util.Calendar;

public class ChartView extends View {

  private float dx, dy;
  private int[] pointChart = {0, 0, 0, 0, 0, 0, 0};
  private int maxValue = 100;
  private boolean isScale = true;
  private String daySun = "Sun";
  private String dayMon = "Mon";
  private String dayTue = "Tue";
  private String dayWed = "Wed";
  private String dayThu = "Thu";
  private String dayFri = "Fri";
  private String daySat = "Sat";
  private String[] days = {daySun, dayMon, dayTue, dayWed, dayThu, dayFri, daySat};
  private int indexCurrentDay = 6;
  private Paint paintChart;
  private int marginBottomChart = DimensUtils.dpToPx(getContext(), 16);
  private int marginBottomText = DimensUtils.dpToPx(getContext(), 16);
  private int colorStart = Color.parseColor("#8FA359");
  private int colorEnd = Color.parseColor("#31EDD7");
  private int lineDash = DimensUtils.dpToPx(getContext(), 2);
  private int sizeText = DimensUtils.dpToPx(getContext(), 10);
  private int radius = DimensUtils.dpToPx(getContext(), 7);
  private int maxWidth = DimensUtils.dpToPx(getContext(), 500);
  private int maxHeight = DimensUtils.dpToPx(getContext(), 200);

  public ChartView(Context context) {
    super(context);
  }

  public ChartView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
    int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);

    if (widthMode == MeasureSpec.AT_MOST) {
      if (parentWidth > maxWidth) {
        parentWidth = maxWidth;
      }
    }

    if (heightMode == MeasureSpec.AT_MOST) {
      if (parentHeight > maxHeight) {
        parentHeight = maxHeight;
      }
    }
    this.setMeasuredDimension(parentWidth, parentHeight);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    onInit();

    Path path = new Path();
    int size = pointChart.length;

    for (int i = 0; i < size; i++) {
      drawLine(path, i, pointChart[i]);
    }

    for (int i = 0; i < days.length; i++) {
      drawText(canvas, days[i], i);
    }

    canvas.drawPath(path, paintChart);
    drawCircle(canvas, size - 1, pointChart[size - 1]);
    drawVerticalLine(canvas, size - 1, pointChart[size - 1]);
  }

  private void onInit() {
    if (isScale) {
      getMaxValue(pointChart);
    }
    initCurrentDay();
    initDate();

    paintChart = new Paint();
    paintChart.setAntiAlias(true);
    paintChart.setStrokeWidth(DimensUtils.dpToPx(getContext(), 3));
    paintChart.setStyle(Style.STROKE);
    paintChart
        .setShader(new LinearGradient(0, 0, getWidth(), getHeight(), colorStart, colorEnd, Shader.TileMode.MIRROR));
    dx = (getWidth() - radius * 4) / (float) 6;
    dy = (getHeight() - radius * 2 - marginBottomChart - marginBottomText) / (float) maxValue;
    this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
  }

  private void initCurrentDay() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(System.currentTimeMillis());
    indexCurrentDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
  }

  private void initDate() {
    switch (indexCurrentDay) {
      case 0:
        days = new String[]{dayMon, dayTue, dayWed, dayThu, dayFri, daySat, daySun};
        break;
      case 1:
        days = new String[]{dayTue, dayWed, dayThu, dayFri, daySat, daySun, dayMon};
        break;
      case 2:
        days = new String[]{dayWed, dayThu, dayFri, daySat, daySun, dayMon, dayTue};
        break;
      case 3:
        days = new String[]{dayThu, dayFri, daySat, daySun, dayMon, dayTue, dayWed};
        break;
      case 4:
        days = new String[]{dayFri, daySat, daySun, dayMon, dayTue, dayWed, dayThu};
        break;
      case 5:
        days = new String[]{daySat, daySun, dayMon, dayTue, dayWed, dayThu, dayFri};
        break;
      case 6:
        days = new String[]{daySun, dayMon, dayTue, dayWed, dayThu, dayFri, daySat};
        break;
    }
  }

  private void drawLine(Path path, int position, int percent) {
    float x = getX(position);
    float y = getY(percent);

    if (position == 0) {
      path.moveTo(x, y);
    } else {
      path.lineTo(x, y);
    }
  }

  private void drawCircle(Canvas canvas, int position, int percent) {
    float x = getX(position);
    float y = getY(percent);
    Paint paint = new Paint();
    paint.setColor(colorEnd);
    paint.setAntiAlias(true);
    canvas.drawCircle(x, y, radius, paint);
  }

  private void drawVerticalLine(Canvas canvas, int position, int percent) {
    float x = getX(position);
    float y = getY(percent);

    Paint paint = new Paint();
    paint.setColor(colorEnd);
    paint.setStrokeWidth(DimensUtils.dpToPx(getContext(), 0.5f));
    paint.setStyle(Style.STROKE);
    paint.setAntiAlias(true);
    paint.setPathEffect(new DashPathEffect(new float[]{lineDash, lineDash}, 0));
    canvas.drawLine(x, y + radius * 2, x, getY(0), paint);
  }

  private void drawText(Canvas canvas, String text, int position) {
    float x = getX(position);
    Paint paint = new Paint();
    paint.setColor(Color.WHITE);
    paint.setAlpha(100);
    paint.setTextAlign(Align.CENTER);
    paint.setTextSize(sizeText);

    canvas.drawText(text, x, getHeight() - marginBottomText, paint);
  }

  private float getX(int position) {
    return position * dx + radius * 2;
  }

  private float getY(int percent) {
    return getHeight() - percent * dy - radius - marginBottomChart - marginBottomText;
  }

  private int getMaxValue(int[] pointChart) {
    maxValue = 0;
    for (int aPointChart : pointChart) {
      if (maxValue < aPointChart) {
        maxValue = aPointChart;
      }
    }
    if (maxValue == 0) {
      maxValue = 1;
    }
    return maxValue;
  }

  public void setPointChart(int[] pointChart, boolean isScale) {
    this.pointChart = pointChart;
    this.isScale = isScale;
    if (isScale) {
      getMaxValue(pointChart);
    }
    invalidate();
  }
}
