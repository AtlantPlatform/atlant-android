/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.receive.ReceivePresenter;
import io.atlant.wallet.activities.receive.ReceivePresenterImpl;
import io.atlant.wallet.activities.receive.ReceiveView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.receive.ReceivePresenter;
import io.atlant.wallet.activities.receive.ReceivePresenterImpl;
import io.atlant.wallet.activities.receive.ReceiveView;

@Module
public class ReceiveActivityModule {

  private ReceiveView view;

  public ReceiveActivityModule(ReceiveView view) {
    this.view = view;
  }

  @Provides
  ReceiveView provideView() {
    return view;
  }

  @Provides
  ReceivePresenter providePresenter(ReceivePresenterImpl presenter) {
    return presenter;
  }

}
