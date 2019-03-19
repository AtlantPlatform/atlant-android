/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.import_wallet.ImportPresenter;
import io.atlant.wallet.activities.import_wallet.ImportPresenterImpl;
import io.atlant.wallet.activities.import_wallet.ImportView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.import_wallet.ImportPresenter;
import io.atlant.wallet.activities.import_wallet.ImportPresenterImpl;
import io.atlant.wallet.activities.import_wallet.ImportView;

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
