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

package com.frostchein.atlant.activities.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.activities.camera.CameraActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerHomeActivityComponent;
import com.frostchein.atlant.dagger2.component.HomeActivityComponent;
import com.frostchein.atlant.dagger2.modules.HomeActivityModule;
import com.frostchein.atlant.fragments.transactions.TransactionsFragment;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.views.BaseCustomView;
import com.frostchein.atlant.views.NoTransactionView;
import com.frostchein.atlant.views.ToolbarWalletView;
import java.util.ArrayList;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

public class HomeActivity extends BaseActivity implements HomeView, ToolbarWalletView.CallBack {

  @Inject
  HomePresenter presenter;
  @Inject
  ToolbarWalletView toolbarWalletView;

  private TransactionsFragment transactionsFragment;

  @BindView(R.id.screen_overlay_view)
  FrameLayout screenOverlayView;
  @BindView(R.id.fragment_content_frame)
  FrameLayout fragmentContentFrame;
  @BindView(R.id.no_transactions_view)
  NoTransactionView noTransactionView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_homescreen);
    setToolbarTitle(R.string.menu_balance);
    toolbarWalletView.deleteTitle();

    transactionsFragment = TransactionsFragment.newInstance();
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_content_frame, transactionsFragment, TransactionsFragment.class.getName())
        .commit();

    presenter.onCreate(savedInstanceState);
    EventBus.getDefault().register(presenter);
  }

  @Override
  public void onPause() {
    toolbarWalletView.setCallback(null);
    super.onPause();
  }

  @Override
  public void onResume() {
    super.onResume();
    toolbarWalletView.setCallback(this);
  }

  @Override
  protected void onDestroy() {
    EventBus.getDefault().unregister(presenter);
    super.onDestroy();
  }

  @Override
  public void onBackPressed() {
    if (drawerHelper.isDrawerOpen()) {
      drawerHelper.closeDrawer();
    } else {
      ActivityCompat.finishAffinity(this);
    }
  }

  @Override
  public void initUI() {
    screenOverlayView.setVisibility(View.GONE);
  }

  @Override
  public void setTransactionsOnFragment(ArrayList<Object> arrayList, int[] pointChart) {
    enableScrollToolbar();
    toolbarWalletView.updateChart(pointChart);
    fragmentContentFrame.setVisibility(View.VISIBLE);
    noTransactionView.setVisibility(View.GONE);
    transactionsFragment.update(arrayList);
  }

  @Override
  public void setNoTransactionsOnView(int[] pointChart) {
    disableScrollToolbar();
    toolbarWalletView.updateChart(pointChart);
    fragmentContentFrame.setVisibility(View.GONE);
    noTransactionView.setVisibility(View.VISIBLE);
    noTransactionView.invalidate();
  }

  @Override
  public void onLoadingError() {
    showMessage(getString(R.string.system_wallet_loading_error));
  }

  @Override
  public void onTimeout() {
    showMessage(getString(R.string.system_timeout));
  }

  @Override
  public void onStartActivityBackup() {
    goToBackupActivity(false);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    HomeActivityComponent component = DaggerHomeActivityComponent.builder()
        .appComponent(appComponent)
        .homeActivityModule(new HomeActivityModule(this))
        .build();
    component.inject(this);
  }

  @Override
  public boolean useToolbar() {
    return true;
  }

  @Override
  public boolean useDrawer() {
    return true;
  }

  @Override
  public boolean useToolbarActionHome() {
    return false;
  }

  @Override
  public boolean useToolbarActionQRCode() {
    return true;
  }

  @Override
  public boolean useCustomToolbar() {
    return true;
  }

  @Override
  public boolean useSwipeRefresh() {
    return true;
  }

  @Override
  protected void onToolbarQR() {
    super.onToolbarQR();
    goToCameraActivity(false, CameraActivity.TAG_FROM_HOME);
  }

  @Override
  public boolean timerLogOut() {
    return true;
  }

  @Override
  protected BaseCustomView getCustomToolbar() {
    return toolbarWalletView;
  }

  @Override
  public void setContentOnToolbar(Balance balance) {
    toolbarWalletView.setContent(balance);
  }

  @Override
  protected void onRefreshSwipe() {
    presenter.refreshContent();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if ((requestCode == BaseActivity.REQUEST_CODE_SEND || requestCode == BaseActivity.REQUEST_CODE_CAMERA)
        && resultCode == Activity.RESULT_OK) {
      presenter.onUpdateLocal();
    }
  }

  @Override
  public void onItemsClick(int pos) {
    presenter.onChangeValue(pos);
  }
}
