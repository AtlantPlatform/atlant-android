/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.login_selected;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.activities.login.LoginActivity;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerLoginSelectedActivityComponent;
import io.atlant.wallet.dagger2.component.LoginSelectedActivityComponent;
import io.atlant.wallet.dagger2.modules.LoginSelectedActivityModule;
import io.atlant.wallet.utils.FontsUtils;
import javax.inject.Inject;

public class LoginSelectedActivity extends BaseActivity implements LoginSelectedView {

  @Inject
  LoginSelectedPresenter presenter;

  @BindView(R.id.name)
  TextView textName;
  @BindView(R.id.login_selected_bt_create)
  Button btCreate;
  @BindView(R.id.login_selected_bt_import)
  Button btImport;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_selected);
    FontsUtils.toOctarineBold(getContext(), textName);
  }

  @OnClick(R.id.login_selected_bt_create)
  public void onClickCreate() {
    goToLoginActivity(false, LoginActivity.TYPE_AUTHORISATION_FROM_SELECTED);
  }

  @OnClick(R.id.login_selected_bt_import)
  public void onClickImport() {
    goToImportWalletActivity(false);
  }

  @Override
  public void initUI() {

  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    //ActivityCompat.finishAffinity(this);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    LoginSelectedActivityComponent component = DaggerLoginSelectedActivityComponent.builder()
        .appComponent(appComponent)
        .loginSelectedActivityModule(new LoginSelectedActivityModule(this))
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
