/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.home.HomePresenter;
import io.atlant.wallet.activities.home.HomePresenterImpl;
import io.atlant.wallet.activities.home.HomeView;
import io.atlant.wallet.views.ToolbarWalletView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.home.HomePresenter;
import io.atlant.wallet.activities.home.HomePresenterImpl;
import io.atlant.wallet.activities.home.HomeView;

@Module
public class HomeActivityModule {

  private HomeView view;

  public HomeActivityModule(HomeView view) {
    this.view = view;
  }

  @Provides
  HomeView provideView() {
    return view;
  }

  @Provides
  HomePresenter providePresenter(HomePresenterImpl presenter) {
    return presenter;
  }

  @Provides
  ToolbarWalletView provideToolbar() {
    return new ToolbarWalletView(view.getContext());
  }

}
