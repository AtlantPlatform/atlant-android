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

package io.atlant.wallet.activities.send;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import io.atlant.wallet.activities.camera.CameraActivity;

import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.activities.camera.CameraActivity;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerSendActivityComponent;
import io.atlant.wallet.dagger2.component.SendActivityComponent;
import io.atlant.wallet.dagger2.modules.SendActivityModule;
import io.atlant.wallet.model.Balance;
import io.atlant.wallet.utils.ConnectivityUtils;
import io.atlant.wallet.utils.DecimalDigitsInputFilter;
import io.atlant.wallet.utils.DialogUtils;
import io.atlant.wallet.utils.IntentUtils;
import io.atlant.wallet.utils.IntentUtils.EXTRA_STRING;
import io.atlant.wallet.views.BaseCustomView;
import io.atlant.wallet.views.ToolbarWalletView;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

public class SendActivity extends BaseActivity implements SendView, ToolbarWalletView.CallBack {

  @Inject
  SendPresenter presenter;
  @Inject
  ToolbarWalletView toolbarWalletView;

  @BindView(R.id.send_address_edit)
  EditText editAddress;
  @BindView(R.id.send_value)
  EditText editValue;

  private String walletName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_send);
    setToolbarTitle(R.string.send_title);
    toolbarWalletView.deleteChart();
    presenter.onCreate(getIntent().getStringExtra(EXTRA_STRING.ADDRESS));
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
  public void initUI() {
    editValue.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(100, 18)});
  }

  @Override
  protected void onDestroy() {
    EventBus.getDefault().unregister(presenter);
    super.onDestroy();
  }

  @Override
  protected BaseCustomView getCustomToolbar() {
    return toolbarWalletView;
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    SendActivityComponent component = DaggerSendActivityComponent.builder()
        .appComponent(appComponent)
        .sendActivityModule(new SendActivityModule(this))
        .build();
    component.inject(this);
  }

  @Override
  public boolean useToolbar() {
    return true;
  }

  @Override
  public boolean useDrawer() {
    return false;
  }

  @Override
  public boolean useToolbarActionHome() {
    return true;
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
  public boolean timerLogOut() {
    return true;
  }

  @Override
  protected void onRefreshSwipe() {
    presenter.refreshContent();
  }

  @Override
  protected void onToolbarQR() {
    super.onToolbarQR();
    goToCameraActivity(false, CameraActivity.TAG_FROM_SEND);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == BaseActivity.REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
      presenter.onCreate(data.getStringExtra(IntentUtils.EXTRA_STRING.ADDRESS));
    }
  }

  @OnClick(R.id.bt_close)
  void OnClick() {
    presenter.onValidate();
  }

  @Override
  public void setAddress(String address) {
    editAddress.setText(address);
  }

  @Override
  public void setValue(String SWValue) {
    editValue.setText(SWValue);
  }

  @Override
  public void setBalance(Balance balance) {
    toolbarWalletView.setContent(balance);
  }

  @Override
  public void setWalletName(String walletName) {
    this.walletName = walletName;
  }

  @Override
  public String getAddress() {
    return editAddress.getText().toString();
  }

  @Override
  public String getValue() {
    return editValue.getText().toString();
  }

  @Override
  public void onError(String message) {
    onScreenError(message);
  }

  @Override
  public void onTimeout() {
    onScreenError(getString(R.string.system_timeout));
  }

  @Override
  public void setContentOnToolbar(Balance balance) {
    setResult(RESULT_OK);
    toolbarWalletView.setContent(balance);
  }

  @Override
  public void onSuccessfulSend() {
    showMessage(getString(R.string.send_successful_send));
    setResult(RESULT_OK);
    finish();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  @Override
  public void dialogConfirmTransaction() {
    String text = String.format(getString(R.string.send_dialog_text_warning),
        editValue.getText(), walletName, editAddress.getText());
    DialogUtils.openDialogChoice(getContext(), R.string.app_name, text, R.string.bt_dialog_continue,
        R.string.bt_dialog_back, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            DialogUtils.hideDialog();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
              @Override
              public void run() {
                if (!ConnectivityUtils.isNetworkOnline(getContext())) {
                  onNoInternetConnection();
                } else {
                  presenter.onSendTransaction();
                }
              }
            }, 50);
          }
        });
  }

  @Override
  public void onNoMoney() {
    onScreenError(getString(R.string.send_no_money));
  }

  @Override
  public void onFormatMoney() {
    onScreenError(String.format(getString(R.string.send_no_correct_money), walletName));
  }

  @Override
  public void onInvalidAddress() {
    onScreenError(getString(R.string.send_invalid_data));
  }

  @Override
  public void onItemsClick(int pos) {
    presenter.onChangeValue(pos);
  }
}
