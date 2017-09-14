package com.frostchein.atlant.activities.home;

import android.os.Bundle;
import com.frostchein.atlant.activities.base.BasePresenter;

public interface HomePresenter extends BasePresenter {

  void onCreate(Bundle savedInstanceState);

  void onChangeValue(int pos);

  void onUpdateLocal();

  void refreshContent();

}
