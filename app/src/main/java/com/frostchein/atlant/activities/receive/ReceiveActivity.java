package com.frostchein.atlant.activities.receive;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerReceiveActivityComponent;
import com.frostchein.atlant.dagger2.component.ReceiveActivityComponent;
import com.frostchein.atlant.dagger2.modules.ReceiveActivityModule;
import com.frostchein.atlant.utils.AnimationUtils;
import javax.inject.Inject;

public class ReceiveActivity extends BaseActivity implements ReceiveView {

  @Inject
  ReceivePresenter presenter;

  @BindView(R.id.receive_qrcode_image)
  ImageView imageQrCode;
  @BindView(R.id.receive_address_text)
  TextView textAddress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_receive);
    setToolbarTitle(R.string.receive_title);
  }

  @Override
  protected void initUI() {
    presenter.onCreated();
    imageQrCode.setImageBitmap(null);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    ReceiveActivityComponent component = DaggerReceiveActivityComponent.builder()
        .appComponent(appComponent)
        .receiveActivityModule(new ReceiveActivityModule(this))
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
  public void onKeyCopied() {
    AnimationUtils.copyBufferText(textAddress, 500);
    showMessage(getString(R.string.system_key_copied));
  }

  @Override
  public void setQR(Bitmap bitmap) {
    imageQrCode.setImageBitmap(bitmap);
  }

  @Override
  public void setAddress(String address) {
    textAddress.setText(address);
  }


  @OnClick(R.id.receive_address_text)
  public void OnClickAddress() {
    presenter.onCopyAddress();
  }
}
