package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.settings.SettingsPresenter;
import com.frostchein.atlant.activities.settings.SettingsPresenterImpl;
import com.frostchein.atlant.activities.settings.SettingsView;
import dagger.Module;
import dagger.Provides;

@Module
public class SettingsActivityModule {

  private SettingsView view;

  public SettingsActivityModule(SettingsView view) {
    this.view = view;
  }

  @Provides
  SettingsView provideView() {
    return view;
  }

  @Provides
  SettingsPresenter providePresenter(SettingsPresenterImpl presenter) {
    return presenter;
  }

}
