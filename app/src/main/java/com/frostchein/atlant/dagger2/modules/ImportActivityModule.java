package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.import_wallet.ImportPresenter;
import com.frostchein.atlant.activities.import_wallet.ImportPresenterImpl;
import com.frostchein.atlant.activities.import_wallet.ImportView;
import dagger.Module;
import dagger.Provides;

@Module
public class ImportActivityModule {

  private ImportView view;

  public ImportActivityModule(ImportView view) {
    this.view = view;
  }

  @Provides
  ImportView provideView() {
    return view;
  }

  @Provides
  ImportPresenter providePresenter(ImportPresenterImpl presenter) {
    return presenter;
  }

}
