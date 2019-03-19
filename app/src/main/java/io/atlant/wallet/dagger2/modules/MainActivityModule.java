/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.main.MainPresenter;
import io.atlant.wallet.activities.main.MainPresenterImpl;
import io.atlant.wallet.activities.main.MainView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.main.MainPresenter;
import io.atlant.wallet.activities.main.MainPresenterImpl;
import io.atlant.wallet.activities.main.MainView;

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
