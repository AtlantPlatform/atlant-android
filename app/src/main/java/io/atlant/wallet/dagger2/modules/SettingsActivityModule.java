/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.settings.SettingsPresenter;
import io.atlant.wallet.activities.settings.SettingsPresenterImpl;
import io.atlant.wallet.activities.settings.SettingsView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.settings.SettingsPresenter;
import io.atlant.wallet.activities.settings.SettingsPresenterImpl;
import io.atlant.wallet.activities.settings.SettingsView;

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
