package com.frostchein.atlant.activities.login_selected_app;

import com.frostchein.atlant.activities.base.BaseView;
import com.frostchein.atlant.adapters.SelectedAppPagerAdapter;

public interface LoginSelectedAppView extends BaseView {

  void setAdapter(SelectedAppPagerAdapter adapter);

  void startWallet();

  void startRentals();

  void startTrade();

}
