package com.frostchein.atlant.utils;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import com.frostchein.atlant.R;

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
}
