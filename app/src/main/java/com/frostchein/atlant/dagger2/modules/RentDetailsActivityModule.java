package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.rent_details.RentDetailsPresenter;
import com.frostchein.atlant.activities.rent_details.RentDetailsPresenterImpl;
import com.frostchein.atlant.activities.rent_details.RentDetailsView;
import dagger.Module;
import dagger.Provides;

@Module
public class RentDetailsActivityModule {

  private RentDetailsView view;

  public RentDetailsActivityModule(RentDetailsView view) {
    this.view = view;
  }

  @Provides
  RentDetailsView provideView() {
    return view;
  }

  @Provides
  RentDetailsPresenter providePresenter(RentDetailsPresenterImpl presenter) {
    return presenter;
  }
}
