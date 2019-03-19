/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.activities.login.LoginPresenter;
import io.atlant.wallet.activities.login.LoginPresenterImpl;
import io.atlant.wallet.activities.login.LoginView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.activities.login.LoginPresenter;
import io.atlant.wallet.activities.login.LoginPresenterImpl;
import io.atlant.wallet.activities.login.LoginView;

@Module
public class LoginActivityModule {

  private LoginView view;

  public LoginActivityModule(LoginView view) {
    this.view = view;
  }

  @Provides
  LoginView provideView() {
    return view;
  }

  @Provides
  LoginPresenter providePresenter(LoginPresenterImpl presenter) {
    return presenter;
  }

}
