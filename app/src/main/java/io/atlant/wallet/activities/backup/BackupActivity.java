/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.backup;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.BackupActivityComponent;
import io.atlant.wallet.dagger2.component.DaggerBackupActivityComponent;
import io.atlant.wallet.dagger2.modules.BackupActivityModule;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.DimensUtils;
import javax.inject.Inject;

public class BackupActivity extends BaseActivity implements BackupView {

  @Inject
  BackupPresenter presenter;

  @BindView(R.id.backup_qr_image)
  ImageView imageQrCode;
  @BindView(R.id.backup_key_text)
  TextView textPrivate;
  @BindView(R.id.bt_done)
  Button btDone;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_backup);
    setToolbarTitle(R.string.backup_title);
    presenter.onCreate();
  }

  @Override
  public void initUI() {
    imageQrCode.setImageBitmap(null);
    presenter.checked(false);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    BackupActivityComponent component = DaggerBackupActivityComponent.builder()
        .appComponent(appComponent)
        .backupActivityModule(new BackupActivityModule(this))
        .build();
    component.inject(this);
  }

  @OnClick(R.id.bt_done)
  public void onClickDone() {
    presenter.onDone();
  }

  @OnCheckedChanged(R.id.backup_checked)
  void onGenderSelected(CompoundButton button, boolean checked) {
    presenter.checked(checked);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    ActivityCompat.finishAffinity(this);
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
    return CredentialHolder.isLogged();
  }

  @Override
  public void setQR(Bitmap bitmap) {
    imageQrCode.setImageBitmap(bitmap);
  }

  @Override
  public void setPrivateKey(String privateKey) {
    textPrivate.setText(privateKey);
  }

  @Override
  public void stateBtContinue(boolean state) {
    if (!state) {
      btDone.setAlpha(
          DimensUtils.getFloatFromResources(getContext(), R.dimen.system_bt_disable_opacity));
    } else {
      btDone.setAlpha(
          DimensUtils.getFloatFromResources(getContext(), R.dimen.system_bt_normal_opacity));
    }
    btDone.setEnabled(state);
  }

  @Override
  public void onFinish() {
    finish();
  }
}
