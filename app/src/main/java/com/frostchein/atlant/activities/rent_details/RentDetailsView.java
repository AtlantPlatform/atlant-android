package com.frostchein.atlant.activities.rent_details;

import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.widget.LinearLayout;
import com.frostchein.atlant.activities.base.BaseView;
import java.util.ArrayList;

public interface RentDetailsView extends BaseView {

  void setName(String name);

  void setAddress(String address);

  void setRooms(String text);

  void setGuests(String text);

  void setBeds(String text);

  void setBath(String text);

  void setLikes(int number);

  void setUrlImages(ArrayList<String> arrayListURL);

  void setHowDay(String text);

  void setPrice(String price);

  void setCoordinates(double var1, double var2);

  void setAlphaFooter(float alpha);

  void setDescription(String text);

  int getSizeFooter();

  ViewPager getViewPager();

  LinearLayout getLinearViewPager();

  NestedScrollView getNestedScrollView();

  AppBarLayout getAppBarLayout();

  void startAnimation();

  void removeAmenitiesTV();

  void removeAmenitiesElevator();

  void removeAmenitiesWiFi();

  void removeAmenitiesKitchen();

}
