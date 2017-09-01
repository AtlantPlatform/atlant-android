package com.frostchein.atlant.activities.login;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerLoginActivityComponent;
import com.frostchein.atlant.dagger2.component.LoginActivityComponent;
import com.frostchein.atlant.dagger2.modules.LoginActivityModule;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.IntentUtil;
import com.frostchein.atlant.utils.IntentUtil.EXTRA_STRING;
import com.frostchein.atlant.views.LoginKeyboardView;
import com.frostchein.atlant.views.PasswordView;
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
    type = getIntent().getIntExtra(IntentUtil.EXTRA_STRING.TYPE_RESULT, type);
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
