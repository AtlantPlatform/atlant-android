package com.frostchein.atlant.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.frostchein.atlant.utils.DimensUtils;

public class ChartView extends View {

  private float dx, dy;
  private int[] pointGraph = {0, 0, 0, 100, 0, 0};
  private Paint paintGraph;
  private int colorStart = Color.parseColor("#8FA359");
  private int colorEnd = Color.parseColor("#31EDD7");
  private int paddingTopBottom = DimensUtils.dpToPx(getContext(), 10);
  private int radius = DimensUtils.dpToPx(getContext(), 8);

  public ChartView(Context context) {
    super(context);
  }

  public ChartView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
    int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
    this.setMeasuredDimension(parentWidth, parentHeight);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    onInit();

    Path path = new Path();
    int size = pointGraph.length;

    for (int i = 0; i < size; i++) {
      drawLine(path, i, pointGraph[i]);
    }
    canvas.drawPath(path, paintGraph);
    drawCircle(canvas, size - 1, pointGraph[size - 1]);
  }

  private void onInit() {
    paintGraph = new Paint();
    paintGraph.setAntiAlias(true);
    paintGraph.setStrokeWidth(DimensUtils.dpToPx(getContext(), 3));
    paintGraph.setStyle(Style.STROKE);
    paintGraph
        .setShader(new LinearGradient(0, 0, getWidth(), getHeight(), colorStart, colorEnd, Shader.TileMode.MIRROR));
    dx = getWidth() / (float) 6;
    dy = (getHeight()-radius) / (float) 100;

  }

  private void drawLine(Path path, int position, int percent) {
    float x = position * dx;
    float y = getHeight() - percent * dy - radius*2;

    if (position == 0) {
      path.moveTo(x, y);
    } else {
      path.lineTo(x, y);
    }
  }

  private void drawCircle(Canvas canvas, int position, int percent) {
    float x = position * dx;
    float y = getHeight() - percent * dy - radius*2;
    Paint paint = new Paint();
    paint.setColor(colorEnd);
    canvas.drawCircle(x, y, radius, paint);
  }

  public void setPointGraph(int[] pointGraph) {
    this.pointGraph = pointGraph;
    invalidate();
  }
}
