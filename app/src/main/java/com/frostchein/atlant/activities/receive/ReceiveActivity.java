package com.frostchein.atlant.activities.receive;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
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
import com.frostchein.atlant.utils.DecimalDigitsInputFilter;
import javax.inject.Inject;

public class ReceiveActivity extends BaseActivity implements ReceiveView, TextWatcher {

  @Inject
  ReceivePresenter presenter;

  @BindView(R.id.receive_qrcode_image)
  ImageView imageQrCode;
  @BindView(R.id.receive_address_text)
  TextView textAddress;
  @BindView(R.id.receive_value)
  EditText editValue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_receive);
    setToolbarTitle(R.string.receive_title);
  }

  @Override
  protected void initUI() {
    presenter.onCreated();
    editValue.addTextChangedListener(this);
    editValue.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(100, 18)});
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
    AnimationUtils.copyBufferText(getContext(), textAddress);
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

  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    try {
      presenter.onGenerationQR(Double.parseDouble(editValue.getText().toString()));
    } catch (Exception e) {
      presenter.onGenerationQR(0);
    }
  }

  @Override
  public void afterTextChanged(Editable editable) {

  }

  @OnClick(R.id.receive_address_text)
  public void OnClickAddress() {
    presenter.onCopyAddress();
  }
}
