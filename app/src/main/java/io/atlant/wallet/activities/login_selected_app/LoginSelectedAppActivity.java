/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
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
