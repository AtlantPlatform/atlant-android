package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.rent_start.RentStartPresenter;
import com.frostchein.atlant.activities.rent_start.RentStartPresenterImpl;
import com.frostchein.atlant.activities.rent_start.RentStartView;
import dagger.Module;
import dagger.Provides;

@Module
public class RentStartActivityModule {

  private RentStartView view;

  public RentStartActivityModule(RentStartView view) {
    this.view = view;
  }

  @Provides
  RentStartView provideView() {
    return view;
  }

  @Provides
  RentStartPresenter providePresenter(RentStartPresenterImpl presenter) {
    return presenter;
  }

}
