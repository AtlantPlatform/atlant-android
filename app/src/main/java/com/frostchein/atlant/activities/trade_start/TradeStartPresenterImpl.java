package com.frostchein.atlant.activities.trade_start;

import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.utils.ScreenUtils;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

public class TradeStartPresenterImpl implements TradeStartPresenter, BasePresenter {

  private TradeStartView view;

  @Inject
  TradeStartPresenterImpl(TradeStartView view) {
    this.view = view;
  }

  @Override
  public void onCreate() {
    Picasso.with(view.getContext()).load("https://atlant.io/promo/android/999.jpg")
        .resize(ScreenUtils.getWidth(view.getContext()), 0).into(view.getImageView());
  }
}
