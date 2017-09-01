package com.frostchein.atlant.activities.login_selected;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.activities.login.LoginActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerLoginSelectedActivityComponent;
import com.frostchein.atlant.dagger2.component.LoginSelectedActivityComponent;
import com.frostchein.atlant.dagger2.modules.LoginSelectedActivityModule;
import javax.inject.Inject;

public class LoginSelectedActivity extends BaseActivity implements LoginSelectedView {

  @Inject
  LoginSelectedPresenter presenter;

  @BindView(R.id.login_selected_bt_create)
  Button btCreate;
  @BindView(R.id.login_selected_bt_import)
  Button btImport;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_selected);
  }

  @OnClick(R.id.login_selected_bt_create)
  public void onClickCreate() {
    goToLoginActivity(false, LoginActivity.TYPE_AUTHORISATION_FROM_SELECTED);
  }

  @OnClick(R.id.login_selected_bt_import)
  public void onClickImport() {
    goToImportWalletActivity(false);
  }

  @Override
  public void initUI() {

  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    ActivityCompat.finishAffinity(this);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    LoginSelectedActivityComponent component = DaggerLoginSelectedActivityComponent.builder()
        .appComponent(appComponent)
        .loginSelectedActivityModule(new LoginSelectedActivityModule(this))
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
    return false;
  }

}
