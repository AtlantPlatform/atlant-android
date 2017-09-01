package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.home.HomePresenter;
import com.frostchein.atlant.activities.home.HomePresenterImpl;
import com.frostchein.atlant.activities.home.HomeView;
import com.frostchein.atlant.views.AtlToolbarView;
import dagger.Module;
import dagger.Provides;

@Module
public class HomeActivityModule {

  private HomeView view;

  public HomeActivityModule(HomeView view) {
    this.view = view;
  }

  @Provides
  HomeView provideView() {
    return view;
  }

  @Provides
  HomePresenter providePresenter(HomePresenterImpl presenter) {
    return presenter;
  }

  @Provides
  AtlToolbarView provideToolbar() {
    return new AtlToolbarView(view.getContext());
  }

}
