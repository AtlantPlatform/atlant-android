package com.frostchein.atlant.activities.export_wallet;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerExportActivityComponent;
import com.frostchein.atlant.dagger2.component.ExportActivityComponent;
import com.frostchein.atlant.dagger2.modules.ExportActivityModule;
import javax.inject.Inject;

public class ExportActivity extends BaseActivity implements ExportView {

  @Inject
  ExportPresenter presenter;

  @BindView(R.id.export_qr_image)
  ImageView imageQrCode;
  @BindView(R.id.export_private_key_text)
  TextView textPrivate;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_export);
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

  @OnClick(R.id.bt_close)
  public void onClick() {
    finish();
  }

}
