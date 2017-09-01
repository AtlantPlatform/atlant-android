package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.main.MainPresenter;
import com.frostchein.atlant.activities.main.MainPresenterImpl;
import com.frostchein.atlant.activities.main.MainView;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

  private MainView view;

  public MainActivityModule(MainView view) {
    this.view = view;
  }

  @Provides
  MainView provideView() {
    return view;
  }

  @Provides
  MainPresenter providePresenter(MainPresenterImpl presenter) {
    return presenter;
  }

}
