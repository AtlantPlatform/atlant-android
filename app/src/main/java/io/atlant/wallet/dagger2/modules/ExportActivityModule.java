/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.export_wallet.ExportPresenter;
import io.atlant.wallet.activities.export_wallet.ExportPresenterImpl;
import io.atlant.wallet.activities.export_wallet.ExportView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.export_wallet.ExportPresenter;
import io.atlant.wallet.activities.export_wallet.ExportPresenterImpl;
import io.atlant.wallet.activities.export_wallet.ExportView;

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
