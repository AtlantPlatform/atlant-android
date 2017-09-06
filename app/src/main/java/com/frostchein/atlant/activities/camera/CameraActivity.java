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
import com.frostchein.atlant.utils.IntentUtil;
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
    typeResult = getIntent().getIntExtra(IntentUtil.EXTRA_STRING.TYPE_RESULT, typeResult);
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
      goToSendActivity(false, result);
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
