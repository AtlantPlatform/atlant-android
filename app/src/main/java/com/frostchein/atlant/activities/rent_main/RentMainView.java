package com.frostchein.atlant.activities.rent_main;

import com.frostchein.atlant.activities.base.BaseView;
import com.frostchein.atlant.model.rent.Rent;
import com.frostchein.atlant.model.rent.RentCity;
import java.util.ArrayList;

public interface RentMainView extends BaseView {

  void setCategory(int numberCategory);

  void setToolbar(ArrayList<RentCity> arrayList);

  void update(ArrayList<Rent> rents);

  void startActivity(Rent rent);

}
