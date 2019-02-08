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

package io.atlant.wallet.activities.login;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerLoginActivityComponent;
import io.atlant.wallet.dagger2.component.LoginActivityComponent;
import io.atlant.wallet.dagger2.modules.LoginActivityModule;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.IntentUtils;
import io.atlant.wallet.utils.IntentUtils.EXTRA_STRING;
import io.atlant.wallet.utils.FontsUtils;
import io.atlant.wallet.views.LoginKeyboardView;
import io.atlant.wallet.views.PasswordView;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends BaseActivity implements LoginView, TextWatcher {

  public static final int TYPE_AUTHORISATION = 1;
  public static final int TYPE_AUTHORISATION_FROM_SELECTED = 2;
  public static final int TYPE_CHANGE_PASSWORD = 3;
  public static final int TYPE_AUTHORISATION_IMPORT = 4;
  public static final int TYPE_AUTHORISATION_EXPORT = 5;

  @Inject
  LoginPresenter presenter;

  @BindView(R.id.name)
  TextView textName;
  @BindView(R.id.login_password_view)
  PasswordView passwordView;
  @BindView(R.id.login_text_view)
  TextView textView;
  @BindView(R.id.login_password_edit_text)
  EditText passwordEditText;
  @BindView(R.id.login_keyboard)
  LoginKeyboardView loginKeyboardView;


  private int type = TYPE_CHANGE_PASSWORD;
  private String privateKey = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    EventBus.getDefault().register(presenter);
    FontsUtils.toOctarineBold(getContext(), textName);
  }

  @Override
  protected void onDestroy() {
    EventBus.getDefault().unregister(presenter);
    super.onDestroy();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    if (type == TYPE_AUTHORISATION) {
      ActivityCompat.finishAffinity(this);
    }
  }

  @Override
  public void initUI() {
    type = getIntent().getIntExtra(IntentUtils.EXTRA_STRING.TYPE_RESULT, type);
    if (type == TYPE_AUTHORISATION_IMPORT) {
      privateKey = getIntent().getStringExtra(EXTRA_STRING.PRIVATE_KEY);
    }
    loginKeyboardView.setContent(passwordEditText);
    passwordEditText.addTextChangedListener(this);
    presenter.onCreate(type, privateKey);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    LoginActivityComponent component = DaggerLoginActivityComponent.builder()
        .appComponent(appComponent)
        .loginActivityModule(new LoginActivityModule(this))
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
  public void setLoginText(String text) {
    textView.setText(text);
  }

  @Override
  public void requestPasswordState(boolean viewState) {
    passwordEditText.setText(null);
    if (!viewState) {
      presenter.dropLogin();
      presenter.onCreate(type, privateKey);
    }
  }

  @Override
  public void onWrongPassword() {
    showMessage(getString(R.string.login_message_wrong_password));
    if (type == TYPE_CHANGE_PASSWORD || type == TYPE_AUTHORISATION_EXPORT) {
      finish();
    }
  }

  @Override
  public void onSuccessfulAuthorisation(String password) {
    if (type == TYPE_AUTHORISATION || type == TYPE_AUTHORISATION_FROM_SELECTED) {
      goToHomeActivity(true);
    }

    if (type == TYPE_CHANGE_PASSWORD) {
      requestPasswordState(false);
      setLoginText(getString(R.string.login_change_new_password));
    }

    if (type == TYPE_AUTHORISATION_IMPORT) {
      goToHomeActivity(true);
    }

    if (type == TYPE_AUTHORISATION_EXPORT) {
      goToExportActivity(true);
    }
  }

  @Override
  public void onSuccessfulChangePassword(String password) {
    if (type == TYPE_CHANGE_PASSWORD) {
      showMessage(getString(R.string.login_message_successfully_change_password));
      finish();
    }
    if (type == TYPE_AUTHORISATION_IMPORT) {
      goToHomeActivity(true);
    }
  }

  @Override
  public String getPassword() {
    return passwordEditText.getText().toString();
  }

  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    int length = charSequence.length();
    int maxPassword = 6;
    if (length < maxPassword) {
      passwordView.fillPasswordItems(charSequence.length());
    }
    if (length == maxPassword) {
      passwordView.fillPasswordItems(charSequence.length());
      presenter.onPasswordEntered();
    }
  }

  @Override
  public void afterTextChanged(Editable editable) {

  }
}
