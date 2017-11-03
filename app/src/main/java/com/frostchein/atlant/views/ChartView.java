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

public class ChartView extends View {

  private float dx, dy;
  private int[] pointChart = {0, 0, 0, 0};
  private int maxValue = 100;
  private boolean isScale = true;
  private String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
  private Paint paintChart;
  private int marginBottomChart = DimensUtils.dpToPx(getContext(), 16);
  private int marginBottomText = DimensUtils.dpToPx(getContext(), 26);
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
    paint.setStrokeWidth(DimensUtils.dpToPx(getContext(), 1));
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
