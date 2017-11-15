package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.backup.BackupPresenter;
import com.frostchein.atlant.activities.backup.BackupPresenterImpl;
import com.frostchein.atlant.activities.backup.BackupView;
import dagger.Module;
import dagger.Provides;

@Module
public class BackupActivityModule {

  private BackupView view;

  public BackupActivityModule(BackupView view) {
    this.view = view;
  }

  @Provides
  BackupView provideView() {
    return view;
  }

  @Provides
  BackupPresenter providePresenter(BackupPresenterImpl presenter) {
    return presenter;
  }

}
