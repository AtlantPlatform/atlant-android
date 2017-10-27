package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.rent_main.RentMainPresenter;
import com.frostchein.atlant.activities.rent_main.RentMainPresenterImpl;
import com.frostchein.atlant.activities.rent_main.RentMainView;
import com.frostchein.atlant.views.ToolbarRentView;
import dagger.Module;
import dagger.Provides;

@Module
public class RentMainActivityModule {

  private RentMainView view;

  public RentMainActivityModule(RentMainView view) {
    this.view = view;
  }

  @Provides
  RentMainView provideView() {
    return view;
  }

  @Provides
  RentMainPresenter providePresenter(RentMainPresenterImpl presenter) {
    return presenter;
  }

  @Provides
  ToolbarRentView provideToolbar() {
    return new ToolbarRentView(view.getContext());
  }

}
