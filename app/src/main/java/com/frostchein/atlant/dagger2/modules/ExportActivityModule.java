package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.export_wallet.ExportPresenter;
import com.frostchein.atlant.activities.export_wallet.ExportPresenterImpl;
import com.frostchein.atlant.activities.export_wallet.ExportView;
import dagger.Module;
import dagger.Provides;

@Module
public class ExportActivityModule {

  private ExportView view;

  public ExportActivityModule(ExportView view) {
    this.view = view;
  }

  @Provides
  ExportView provideView() {
    return view;
  }

  @Provides
  ExportPresenter providePresenter(ExportPresenterImpl presenter) {
    return presenter;
  }

}
