package com.frostchein.atlant.activities.rent_details;

import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.model.rent.Rent;

public interface RentDetailsPresenter extends BasePresenter {

  void onCreate(Rent rent);

  void startAnimationDown();

}
