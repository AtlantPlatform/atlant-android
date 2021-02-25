/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.export_wallet;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerExportActivityComponent;
import io.atlant.wallet.dagger2.component.ExportActivityComponent;
import io.atlant.wallet.dagger2.modules.ExportActivityModule;
import javax.inject.Inject;

public class ExportActivity extends BaseActivity implements ExportView {

  @Inject
  ExportPresenter presenter;

  @BindView(R.id.export_qr_image)
  ImageView imageQrCode;
  @BindView(R.id.export_text)
  TextView textPrivate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_export);
    setToolbarTitle(R.string.export_title);
  }

  @Override
  public void initUI() {
    presenter.onCreate();
    imageQrCode.setImageBitmap(null);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    ExportActivityComponent component = DaggerExportActivityComponent.builder()
        .appComponent(appComponent)
        .exportActivityModule(new ExportActivityModule(this))
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
    return true;
  }

  @Override
  public void setQR(Bitmap bitmap) {
    imageQrCode.setImageBitmap(bitmap);
  }

  @Override
  public void setPrivateKey(String privateKey) {
    textPrivate.setText(privateKey);
  }

}
