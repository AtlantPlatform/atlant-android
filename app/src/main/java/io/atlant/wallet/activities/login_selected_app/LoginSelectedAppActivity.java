/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.login_selected_app;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import butterknife.BindView;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.adapters.SelectedAppPagerAdapter;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerLoginSelectedAppActivityComponent;
import io.atlant.wallet.dagger2.component.LoginSelectedAppActivityComponent;
import io.atlant.wallet.dagger2.modules.LoginSelectedAppActivityModule;
import io.atlant.wallet.utils.AnimationUtils.AnimationSelectedAppViewPager;
import io.atlant.wallet.utils.FontsUtils;
import io.atlant.wallet.views.IndicatorCircleView;
import javax.inject.Inject;

public class LoginSelectedAppActivity extends BaseActivity implements LoginSelectedAppView {

  @BindView(R.id.name)
  TextView textName;
  @BindView(R.id.login_selected_app_viewpager)
  ViewPager viewPager;
  @BindView(R.id.login_selected_app_indicator)
  IndicatorCircleView indicatorCircleView;

  @Inject
  LoginSelectedAppPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_selected_app);
    FontsUtils.toOctarineBold(getContext(), textName);
    presenter.onCreate();
  }

  @Override
  public void initUI() {

  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    ActivityCompat.finishAffinity(this);
  }


  @Override
  protected void setupComponent(AppComponent appComponent) {
    LoginSelectedAppActivityComponent component = DaggerLoginSelectedAppActivityComponent.builder()
        .appComponent(appComponent)
        .loginSelectedAppActivityModule(new LoginSelectedAppActivityModule(this))
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

  @Override
  public void setAdapter(SelectedAppPagerAdapter adapter) {
    viewPager.setAdapter(adapter);
    viewPager.setPageTransformer(true, new AnimationSelectedAppViewPager());
    indicatorCircleView.setViewPagerCircle(viewPager);
  }

  @Override
  public void startWallet() {
    goToLoginSelectedActivity(false);
  }

  @Override
  public void startRentals() {
    String appPackageName = "io.atlant.rent";
    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appPackageName);
    if (launchIntent != null) {
      try {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName(appPackageName, appPackageName + ".activities.demo.DemoActivity"));
        startActivity(intent);
      } catch (Exception e) {
        e.printStackTrace();
        startActivity(launchIntent);
      }
    } else {
      String url = "https://play.google.com/store/apps/details?id=" + appPackageName;
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
  }

  @Override
  public void startTrade() {
    showMessage(getString(R.string.system_section_in_development));
  }

}
