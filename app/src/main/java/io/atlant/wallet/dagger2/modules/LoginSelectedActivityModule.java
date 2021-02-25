/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.login_selected.LoginSelectedPresenter;
import io.atlant.wallet.activities.login_selected.LoginSelectedPresenterImpl;
import io.atlant.wallet.activities.login_selected.LoginSelectedView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.login_selected.LoginSelectedPresenter;
import io.atlant.wallet.activities.login_selected.LoginSelectedPresenterImpl;
import io.atlant.wallet.activities.login_selected.LoginSelectedView;

@Module
public class LoginSelectedActivityModule {

  private LoginSelectedView view;

  public LoginSelectedActivityModule(LoginSelectedView view) {
    this.view = view;
  }

  @Provides
  LoginSelectedView provideView() {
    return view;
  }

  @Provides
  LoginSelectedPresenter providePresenter(LoginSelectedPresenterImpl presenter) {
    return presenter;
  }

}
