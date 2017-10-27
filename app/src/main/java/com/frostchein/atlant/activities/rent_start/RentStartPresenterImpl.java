package com.frostchein.atlant.activities.rent_start;

import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.utils.ScreenUtils;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

public class RentStartPresenterImpl implements RentStartPresenter, BasePresenter {

  private RentStartView view;

  @Inject
  RentStartPresenterImpl(RentStartView view) {
    this.view = view;
  }

  @Override
  public void onCreate() {
    Picasso.with(view.getContext()).load("https://rent.atlant.io/assets/images/background.jpg")
        .resize(ScreenUtils.getWidth(view.getContext()), 0).into(view.getImageView());
  }
}
