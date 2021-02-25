/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.BindView;
import io.atlant.wallet.activities.camera.CameraActivity;

import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerHomeActivityComponent;
import io.atlant.wallet.dagger2.component.HomeActivityComponent;
import io.atlant.wallet.dagger2.modules.HomeActivityModule;
import io.atlant.wallet.fragments.transactions.TransactionsFragment;
import io.atlant.wallet.model.Balance;
import io.atlant.wallet.views.BaseCustomView;
import io.atlant.wallet.views.NoTransactionView;
import io.atlant.wallet.views.ToolbarWalletView;
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
