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

package io.atlant.wallet.activities.import_wallet;

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
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.activities.camera.CameraActivity;
import io.atlant.wallet.activities.login.LoginActivity;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerImportActivityComponent;
import io.atlant.wallet.dagger2.component.ImportActivityComponent;
import io.atlant.wallet.dagger2.modules.ImportActivityModule;
import io.atlant.wallet.utils.AnimationUtils;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.DimensUtils;
import io.atlant.wallet.utils.IntentUtils.EXTRA_STRING;
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
          DimensUtils.getFloatFromResources(getContext(), R.dimen.system_bt_disable_opacity));
    } else {
      btNext.setAlpha(
          DimensUtils.getFloatFromResources(getContext(), R.dimen.system_bt_normal_opacity));
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
