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