package com.frostchein.atlant.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.frostchein.atlant.R;

public class LikeView extends LinearLayout implements OnClickListener {

  private LinearLayout linearLayout;
  private ImageView imageView;
  private TextView text;
  private int number = 0;
  private boolean isShadows = false;
  private int orientation;

  public LikeView(Context context) {
    super(context);
    initView(context);
  }

  public LikeView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LikeView);
    number = typedArray.getInteger(R.styleable.LikeView_lv_number, number);
    orientation = typedArray.getInt(R.styleable.LikeView_lv_orientation, 0);
    isShadows = typedArray.getBoolean(R.styleable.LikeView_lv_shadows, isShadows);
    initView(context);
  }

  private void initView(Context context) {
    View view = inflate(context, R.layout.view_like, this);
    linearLayout = view.findViewById(R.id.like_linear);
    imageView = view.findViewById(R.id.like_image_view);
    text = view.findViewById(R.id.like_image_text);
    setLike(number);
    setShadows(isShadows);
    linearLayout.setOnClickListener(this);

    if (orientation == 0) {
      linearLayout.setOrientation(VERTICAL);
    }

    if (orientation == 1) {
      linearLayout.setOrientation(HORIZONTAL);

      LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
          LayoutParams.WRAP_CONTENT);
      llp.setMargins(10, 0, 0, 0);
      text.setLayoutParams(llp);
    }
  }

  public void setLike(int number) {
    this.number = number;
    text.setText(String.valueOf(number));
  }

  public int getLike() {
    return number;
  }

  public void setImage(@DrawableRes int drawable) {
    imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), drawable));
  }

  public void setShadows(boolean b) {
    isShadows = b;
    if (b) {
      text.setShadowLayer(5, 1, 1, Color.BLACK);
    } else {
      text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
    }

  }

  @Override
  public void onClick(View view) {
    int time = 200;

    AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
    alphaAnimation.setDuration(time);

    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 0);
    translateAnimation.setDuration(time);

    AnimationSet set = new AnimationSet(true);
    set.addAnimation(alphaAnimation);
    set.addAnimation(translateAnimation);
    linearLayout.startAnimation(set);
    number++;
    setLike(number);
  }
}
