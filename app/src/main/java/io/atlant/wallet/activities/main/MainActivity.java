/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.main;

import android.os.Bundle;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.activities.login.LoginActivity;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerMainActivityComponent;
import io.atlant.wallet.dagger2.component.MainActivityComponent;
import io.atlant.wallet.dagger2.modules.MainActivityModule;
import javax.inject.Inject;

import io.atlant.wallet.activities.login.LoginActivity;

public class MainActivity extends BaseActivity implements MainView {

  @Inject
  MainPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    presenter.onCreate();
  }

  @Override
  public void initUI() {

  }

  @Override
  public void onStartSelected() {
    goToLoginSelectedActivity(true);
  }

  @Override
  public void onStartSelectedApp() {
    goToLoginSelectedAppActivity(true);
  }

  @Override
  public void onStartAuthorisation() {
    goToLoginActivity(true, LoginActivity.TYPE_AUTHORISATION);
  }

  @Override
  public void onStartHome() {
    goToHomeActivity(true);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    MainActivityComponent component = DaggerMainActivityComponent.builder()
        .appComponent(appComponent)
        .mainActivityModule(new MainActivityModule(this))
        .build();
    component.inject(this);
  }

  @Override
  public boolean useToolbar() {
    return false;
  }

  @Override
  public boolean useDrawer() {
    return false;
  }

  @Override
  public boolean useToolbarActionHome() {
    return false;
  }

  @Override
  public boolean useToolbarActionQRCode() {
    return false;
  }

  @Override
  public boolean useCustomToolbar() {
    return false;
  }

  @Override
  public boolean useSwipeRefresh() {
    return false;
  }

  @Override
  public boolean timerLogOut() {
    return false;
  }
}
