/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import atlant.wallet.R;

public class IndicatorCircleView extends LinearLayout implements ViewPager.OnPageChangeListener {

  private int radius = 50;
  private int margin = 10;
  private int max = 5;
  private int colorNormal = Color.WHITE;
  private int colorSelected = Color.BLACK;
  private int current = 3;
  private int k = 0;

  public IndicatorCircleView(Context context) {
    super(context);
    initView(context);
  }

  public IndicatorCircleView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IndicatorCircleView);
    radius = (int) typedArray.getDimension(R.styleable.IndicatorCircleView_icv_radius, radius);
    margin = (int) typedArray.getDimension(R.styleable.IndicatorCircleView_icv_margin, margin);
    max = typedArray.getInteger(R.styleable.IndicatorCircleView_icv_max, max);
    current = typedArray.getInteger(R.styleable.IndicatorCircleView_icv_current, current);
    current--;
    colorNormal = typedArray.getColor(R.styleable.IndicatorCircleView_icv_color_normal, colorNormal);
    colorSelected = typedArray.getColor(R.styleable.IndicatorCircleView_icv_color_selected, colorSelected);
    typedArray.recycle();
    initView(context);
  }

  private void initView(Context context) {
    View view = inflate(context, R.layout.view_indicator_circle_view_page, this);
    LinearLayout linearLayout = view.findViewById(R.id.view_indicator_circle_view_page_layout);
    linearLayout.removeAllViews();

    for (int i = 0 + k; i < max - k; i++) {
      if (i == current) {
        linearLayout.addView(getItemView(context, radius, margin, colorSelected));
      } else {
        linearLayout.addView(getItemView(context, radius, margin, colorNormal));
      }
    }
  }

  private View getItemView(Context context, int size, int margin, int color) {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
    layoutParams.setMargins(margin, 0, margin, 0);
    ViewCircle view = new ViewCircle(context);
    view.setCurrentColor(color);
    view.setLayoutParams(layoutParams);
    return view;
  }

  public void setViewPager(ViewPager viewPager) {
    k = 0;
    viewPager.addOnPageChangeListener(this);
    max = viewPager.getAdapter().getCount();
    current = viewPager.getCurrentItem();
    update();
  }

  /**
   * For circular adapter, where 2 fake page (first and last)
   */
  public void setViewPagerCircle(ViewPager viewPager) {
    k = 1;
    viewPager.addOnPageChangeListener(this);
    max = viewPager.getAdapter().getCount();
    current = viewPager.getCurrentItem();
    if (current == 0) {
      current++;
      viewPager.setCurrentItem(current);
    }
    if (current == max) {
      current--;
      viewPager.setCurrentItem(current);
    }
    update();
  }

  private void update() {
    initView(getContext());
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  public int getMargin() {
    return margin;
  }

  public void setMargin(int margin) {
    this.margin = margin;
  }

  public int getMax() {
    return max;
  }

  public void setMax(int max) {
    this.max = max;
  }

  public int getColorNormal() {
    return colorNormal;
  }

  public void setColorNormal(int colorNormal) {
    this.colorNormal = colorNormal;
  }

  public int getColorSelected() {
    return colorSelected;
  }

  public void setColorSelected(int colorSelected) {
    this.colorSelected = colorSelected;
  }

  public int getCurrent() {
    return current;
  }

  public void setCurrent(int current) {
    this.current = current;
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {
    current = position;

    //for a circular adapter
    if (k == 1) {
      if (current == 0) {
        current++;
      }
      if (current == max) {
        current--;
      }
    }
    update();
  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

  class ViewCircle extends View {

    private int currentColor;

    public ViewCircle(Context context) {
      super(context);
    }

    public ViewCircle(Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
    }

    public void setCurrentColor(int currentColor) {
      this.currentColor = currentColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
      Paint paint = new Paint();
      paint.setAntiAlias(true);
      paint.setColor(currentColor);
      canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
      super.onDraw(canvas);
    }
  }

}
