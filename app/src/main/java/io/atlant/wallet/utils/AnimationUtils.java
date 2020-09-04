/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import atlant.wallet.R;

public class AnimationUtils {

  public static void copyBufferText(final TextView textView, int duration) {
    textView.setEnabled(false);

    int colorFrom = ContextCompat.getColor(textView.getContext(), R.color.colorSecondaryText);
    int colorTo = ContextCompat.getColor(textView.getContext(), R.color.colorPrimaryText);

    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
    colorAnimation.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animator) {
        textView.setTextColor((Integer) animator.getAnimatedValue());
      }
    });
    colorAnimation.addListener(new AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animator) {

      }

      @Override
      public void onAnimationEnd(Animator animator) {
        textView.setEnabled(true);
      }

      @Override
      public void onAnimationCancel(Animator animator) {

      }

      @Override
      public void onAnimationRepeat(Animator animator) {

      }
    });
    colorAnimation.setDuration(duration);
    colorAnimation.start();
  }

  public static void animationScale(View view, float sizeIn, float sizeOut, int duration) {
    ScaleAnimation scaleAnimation = new ScaleAnimation(sizeIn, sizeOut, sizeIn, sizeOut,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    scaleAnimation.setDuration(duration);
    scaleAnimation.setRepeatMode(Animation.REVERSE);
    scaleAnimation.setRepeatCount(Animation.INFINITE);
    view.startAnimation(scaleAnimation);
  }


  public static class AnimationSelectedAppViewPager implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.7f;
    private static final float MIN_ALPHA = 1.0f;

    public void transformPage(View view, float position) {
      int pageWidth = view.getWidth();
      int pageHeight = view.getHeight();
      if (position < -1) {

      } else if (position <= 1) { // [-1,1]
        // Modify the default slide transition to shrink the page as well
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
        float vertMargin = pageHeight * (1 - scaleFactor) / 2;
        float horzMargin = pageWidth * (1 - scaleFactor) / 2;
        if (position < 0) {
          view.setTranslationX(horzMargin - vertMargin / 2);
        } else {
          view.setTranslationX(-horzMargin + vertMargin / 20);
        }

        // Scale the page down (between MIN_SCALE and 1)
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);

        // Fade the page relative to its size.
        view.setAlpha(MIN_ALPHA +
            (scaleFactor - MIN_SCALE) /
                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

      } else {

      }
    }
  }
}
