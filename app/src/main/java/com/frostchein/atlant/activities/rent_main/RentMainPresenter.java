package com.frostchein.atlant.activities.rent_main;

import com.frostchein.atlant.activities.base.BasePresenter;

public interface RentMainPresenter extends BasePresenter {

  void onCreate();

  void onUpdateCity();

  void onUpdate();

  void onSelected(int pos);

  void setCity(int numberCity);

  void setCategory(int numberCategory);

}
