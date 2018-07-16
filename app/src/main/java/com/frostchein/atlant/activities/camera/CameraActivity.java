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

package com.frostchein.atlant.activities.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.CameraActivityComponent;
import com.frostchein.atlant.dagger2.component.DaggerCameraActivityComponent;
import com.frostchein.atlant.dagger2.modules.CameraActivityModule;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.IntentUtils;
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
