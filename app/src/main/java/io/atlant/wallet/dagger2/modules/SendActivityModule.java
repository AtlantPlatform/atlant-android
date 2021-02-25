/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.send.SendPresenter;
import io.atlant.wallet.activities.send.SendPresenterImpl;
import io.atlant.wallet.activities.send.SendView;
import io.atlant.wallet.views.ToolbarWalletView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.send.SendPresenter;
import io.atlant.wallet.activities.send.SendPresenterImpl;
import io.atlant.wallet.activities.send.SendView;

@Module
public class SendActivityModule {

  private SendView view;

  public SendActivityModule(SendView view) {
    this.view = view;
  }

  @Provides
  SendView provideView() {
    return view;
  }

  @Provides
  SendPresenter providePresenter(SendPresenterImpl presenter) {
    return presenter;
  }

  @Provides
  ToolbarWalletView provideToolbar() {
    return new ToolbarWalletView(view.getContext());
  }

}
