package com.frostchein.atlant.activities.rent_details;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.model.rent.Rent;
import com.frostchein.atlant.model.rent.Rent.Amenities;
import com.frostchein.atlant.utils.DimensUtils;
import com.frostchein.atlant.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Inject;

public class RentDetailsPresenterImpl implements RentDetailsPresenter, BasePresenter {

  private RentDetailsView view;
  private Context context;
  private NestedScrollView scrollView;
  private ViewPager viewPager;
  private LinearLayout linearViewPager;

  private float y1, y2;
  private long timeStart, timeEnd;


  @Inject
  RentDetailsPresenterImpl(RentDetailsView view) {
    this.view = view;
  }

  @Override
  public void onCreate(Rent rent) {
    context = view.getContext();

    view.setName(rent.getName());
    view.setUrlImages(new ArrayList<>(Arrays.asList(rent.getImageUrl())));
    view.setPrice("$" + " " + String.valueOf(rent.getPriceDollars()));
    view.setHowDay(rent.getHowDay());
    view.setDescription(rent.getDescription());
    view.setLikes(rent.getNumberLike());
    view.setCoordinates(40.742785, -73.11457);

    view.setAlphaFooter(0);

    view.setAddress(rent.getCountry() + ", " + rent.getCity());
    String rooms = context.getString(R.string.rent_main_rooms);
    view.setRooms(String.valueOf(rent.getNumberRooms()) + " " + rooms);
    String beds = context.getString(R.string.rent_main_beds);
    view.setBeds(String.valueOf(rent.getNumberBeds()) + " " + beds);
    String bath = context.getString(R.string.rent_main_bath);
    view.setBath(String.valueOf(rent.getNumberBath()) + " " + bath);
    String guests = context.getString(R.string.rent_main_guests);
    view.setGuests("1-" + String.valueOf(rent.getNumberMaxGuests()) + " " + guests);

    Amenities amenities = rent.getAmenities();

    if (!amenities.isElevator()) {
      view.removeAmenitiesElevator();
    }

    if (!amenities.isWifi()) {
      view.removeAmenitiesWiFi();
    }

    if (!amenities.isKitchen()) {
      view.removeAmenitiesKitchen();
    }

    if (!amenities.isTv()) {
      view.removeAmenitiesTV();
    }

    viewPager = view.getViewPager();
    linearViewPager = view.getLinearViewPager();
    scrollView = view.getNestedScrollView();
    scrollView.setVerticalScrollBarEnabled(false);
    sizeViewPager(ScreenUtils.getHeight(context) - ScreenUtils.getStatusBarHeight(context));

    scrollView.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {

        if (motionEvent.getActionMasked() == MotionEvent.ACTION_UP) {
          y2 = motionEvent.getY();
          timeEnd = System.currentTimeMillis();

          if ((timeEnd - timeStart) < 200 && Math.abs(y2 - y1) > 100) {
            startAnimationDown();
          }
        }
        return true;
      }
    });

    scrollView.getViewTreeObserver().addOnScrollChangedListener(new OnScrollChangedListener() {
      @Override
      public void onScrollChanged() {
        float alpha = scrollView.getScrollY()-view.getAppBarLayout().getHeight();
        if (alpha > 255) {
          alpha = 255;
        }

        if (alpha < 0) {
          alpha = 0;
        }
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#03354f"));
        paint.setAlpha((int) alpha);

        view.getAppBarLayout().setBackgroundColor(paint.getColor());
      }
    });

    viewPager.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
          y1 = motionEvent.getY();
          timeStart = System.currentTimeMillis();
        }
        return false;
      }
    });
  }

  @Override
  public void startAnimationDown() {
    view.startAnimation();
    final int time = 1000;

    final int dp = 200;
    final int px = DimensUtils.dpToPx(context, dp);
    ValueAnimator animSize = ValueAnimator.ofInt(viewPager.getMeasuredHeight(), px);
    animSize.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int val = (Integer) valueAnimator.getAnimatedValue();
        sizeViewPager(val);
        if (val == px) {
          scrollView.setOnTouchListener(null);
          scrollView.setVerticalScrollBarEnabled(true);
          scrollView.setPadding(0, 0, 0, view.getSizeFooter());
        }

      }
    });
    animSize.setDuration(time);
    animSize.start();

    ValueAnimator animSize2 = ValueAnimator.ofInt(linearViewPager.getMeasuredHeight(), 0);
    animSize2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int val = (Integer) valueAnimator.getAnimatedValue();

        ViewGroup.LayoutParams params = linearViewPager.getLayoutParams();
        params.height = val;
        linearViewPager.setLayoutParams(params);

      }
    });
    animSize2.setDuration(time);
    animSize2.start();

    ValueAnimator animAlphaFooter = ValueAnimator.ofFloat(0, 1);
    animAlphaFooter.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float val = (float) valueAnimator.getAnimatedValue();
        view.setAlphaFooter(val);
      }
    });
    animAlphaFooter.setDuration(time);
    animAlphaFooter.start();

    ValueAnimator animAlphaViewPager = ValueAnimator.ofFloat(0.5f, 1);
    animAlphaViewPager.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float val = (float) valueAnimator.getAnimatedValue();
        viewPager.setAlpha(val);
      }
    });
    animAlphaViewPager.setDuration(time);
    animAlphaViewPager.start();

    AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
    alphaAnimation.setAnimationListener(new AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        linearViewPager.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
    alphaAnimation.setDuration(time);
    linearViewPager.startAnimation(alphaAnimation);

  }

  private void sizeViewPager(int pxHeight) {
    ViewGroup.LayoutParams params = viewPager.getLayoutParams();
    params.height = pxHeight;
    viewPager.setLayoutParams(params);
  }
}
