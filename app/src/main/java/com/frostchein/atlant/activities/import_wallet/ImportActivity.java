package com.frostchein.atlant.activities.import_wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.activities.camera.CameraActivity;
import com.frostchein.atlant.activities.login.LoginActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerImportActivityComponent;
import com.frostchein.atlant.dagger2.component.ImportActivityComponent;
import com.frostchein.atlant.dagger2.modules.ImportActivityModule;
import com.frostchein.atlant.utils.AnimationUtils;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DimensUtil;
import com.frostchein.atlant.utils.IntentUtil.EXTRA_STRING;
import javax.inject.Inject;

public class ImportActivity extends BaseActivity implements ImportView, TextWatcher {

  @Inject
  ImportPresenter presenter;

  @BindView(R.id.import_private_key_edit)
  EditText editPrivateKey;
  @BindView(R.id.import_qr_image)
  ImageView imageQrCode;
  @BindView(R.id.bt_next)
  Button btNext;
  @BindView(R.id.import_qr_anim1_image)
  ImageView imageCircle1;
  @BindView(R.id.import_qr_anim2_image)
  ImageView imageCircle2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_import);
    setToolbarTitle(R.string.import_wallet_title);
    presenter.onCreate();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == BaseActivity.REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
      editPrivateKey.setText(data.getStringExtra(EXTRA_STRING.PRIVATE_KEY));
      presenter.onValidatePrivateKey();
    }
  }

  @Override
  protected void initUI() {
    editPrivateKey.addTextChangedListener(this);
    AnimationUtils.animationScale(imageCircle1, 1f, 0.75f, 1000);
    AnimationUtils.animationScale(imageCircle2, 1f, 0.75f, 1000);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    ImportActivityComponent component = DaggerImportActivityComponent.builder()
        .appComponent(appComponent)
        .importActivityModule(new ImportActivityModule(this))
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
    return CredentialHolder.isLogged();
  }


  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    presenter.onValidatePrivateKey();
  }

  @Override
  public void afterTextChanged(Editable editable) {

  }

  @Override
  public String getPrivateKey() {
    return editPrivateKey.getText().toString();
  }

  @Override
  public void stateBtContinue(boolean state) {
    if (!state) {
      btNext.setAlpha(
          DimensUtil.getFloatFromResources(getContext(), R.dimen.system_bt_disable_opacity));
    } else {
      btNext.setAlpha(
          DimensUtil.getFloatFromResources(getContext(), R.dimen.system_bt_normal_opacity));
    }
    btNext.setEnabled(state);
  }

  @OnClick(R.id.bt_next)
  public void onClickNext() {
    goToLoginActivity(false, LoginActivity.TYPE_AUTHORISATION_IMPORT, editPrivateKey.getText().toString());
  }

  @OnClick(R.id.import_qr_image)
  public void onClickQr() {
    goToCameraActivity(false, CameraActivity.TAG_FROM_IMPORT);
  }

}
