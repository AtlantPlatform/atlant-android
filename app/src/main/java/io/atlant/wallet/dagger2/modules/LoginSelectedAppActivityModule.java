/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.login_selected_app.LoginSelectedAppPresenter;
import io.atlant.wallet.activities.login_selected_app.LoginSelectedAppPresenterImpl;
import io.atlant.wallet.activities.login_selected_app.LoginSelectedAppView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.login_selected_app.LoginSelectedAppPresenter;
import io.atlant.wallet.activities.login_selected_app.LoginSelectedAppPresenterImpl;
import io.atlant.wallet.activities.login_selected_app.LoginSelectedAppView;

@Module
public class LoginSelectedAppActivityModule {

  private LoginSelectedAppView view;

  public LoginSelectedAppActivityModule(LoginSelectedAppView view) {
    this.view = view;
  }

  @Provides
  LoginSelectedAppView provideView() {
    return view;
  }

  @Provides
  LoginSelectedAppPresenter providePresenter(LoginSelectedAppPresenterImpl presenter) {
    return presenter;
  }

}
