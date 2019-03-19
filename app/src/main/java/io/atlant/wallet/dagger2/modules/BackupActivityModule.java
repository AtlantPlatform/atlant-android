/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.backup.BackupPresenter;
import io.atlant.wallet.activities.backup.BackupPresenterImpl;
import io.atlant.wallet.activities.backup.BackupView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.backup.BackupPresenter;
import io.atlant.wallet.activities.backup.BackupPresenterImpl;
import io.atlant.wallet.activities.backup.BackupView;

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
