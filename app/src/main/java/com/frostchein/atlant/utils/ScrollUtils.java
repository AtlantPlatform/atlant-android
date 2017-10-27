package com.frostchein.atlant.utils;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class ScrollUtils {

  public static boolean canScroll(HorizontalScrollView horizontalScrollView) {
    View child = (View) horizontalScrollView.getChildAt(0);
    if (child != null) {
      int childWidth = (child).getWidth();
      return horizontalScrollView.getWidth() < childWidth + horizontalScrollView.getPaddingLeft() + horizontalScrollView
          .getPaddingRight();
    }
    return false;
  }

  public static boolean canScroll(ScrollView scrollView) {
    View child = (View) scrollView.getChildAt(0);
    if (child != null) {
      int childHeight = (child).getHeight();
      return scrollView.getHeight() < childHeight + scrollView.getPaddingTop() + scrollView.getPaddingBottom();
    }
    return false;
  }

}
