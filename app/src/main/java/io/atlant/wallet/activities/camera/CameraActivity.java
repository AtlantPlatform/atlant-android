/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.CameraActivityComponent;
import io.atlant.wallet.dagger2.component.DaggerCameraActivityComponent;
import io.atlant.wallet.dagger2.modules.CameraActivityModule;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.IntentUtils;
import javax.inject.Inject;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraActivity extends BaseActivity implements CameraView {

  @Inject
  CameraPresenter presenter;
  public static final int TAG_FROM_SEND = 1;
  public static final int TAG_FROM_HOME = 2;
  public static final int TAG_FROM_IMPORT = 3;

  private int typeResult = TAG_FROM_SEND;

  @Override
  public void scannerView(ZXingScannerView scannerView) {
    setContentView(scannerView);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_camera);
  }

  @Override
  public void initUI() {
    typeResult = getIntent().getIntExtra(IntentUtils.EXTRA_STRING.TYPE_RESULT, typeResult);
    presenter.onCreate(typeResult);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    CameraActivityComponent component = DaggerCameraActivityComponent.builder()
        .appComponent(appComponent)
        .cameraActivityModule(new CameraActivityModule(this))
        .build();
    component.inject(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.onResume();
  }

  @Override
  public void onPause() {
    presenter.onPause();
    super.onPause();
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
    return CredentialHolder.isLogged();
  }

  @Override
  public void onSuccessfulScanQR(String result) {
    if (typeResult == TAG_FROM_SEND) {
      Intent intent = new Intent();
      intent.putExtra(IntentUtils.EXTRA_STRING.ADDRESS, result);
      setResult(RESULT_OK, intent);
      finish();
    }

    if (typeResult == TAG_FROM_IMPORT) {
      Intent intent = new Intent();
      intent.putExtra(IntentUtils.EXTRA_STRING.PRIVATE_KEY, result);
      setResult(RESULT_OK, intent);
      finish();
    }

    if (typeResult == TAG_FROM_HOME) {
      goToSendActivity(true, result);
    }
  }

  @Override
  public void onFailScanQR() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        showMessage(getString(R.string.camera_failScan));
      }
    });
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == BaseActivity.REQUEST_CODE_SEND && resultCode == Activity.RESULT_OK) {
      setResult(Activity.RESULT_OK);
      finish();
    }
  }
}
