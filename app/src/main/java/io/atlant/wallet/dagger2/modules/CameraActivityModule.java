/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.camera.CameraPresenter;
import io.atlant.wallet.activities.camera.CameraPresenterImpl;
import io.atlant.wallet.activities.camera.CameraView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.camera.CameraPresenter;
import io.atlant.wallet.activities.camera.CameraPresenterImpl;
import io.atlant.wallet.activities.camera.CameraView;

@Module
public class CameraActivityModule {

  private CameraView view;

  public CameraActivityModule(CameraView view) {
    this.view = view;
  }

  @Provides
  CameraView provideView() {
    return view;
  }

  @Provides
  CameraPresenter providePresenter(CameraPresenterImpl presenter) {
    return presenter;
  }

}
