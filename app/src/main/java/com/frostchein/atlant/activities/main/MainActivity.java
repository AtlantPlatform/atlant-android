package com.frostchein.atlant.activities.main;

import android.os.Bundle;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.activities.login.LoginActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerMainActivityComponent;
import com.frostchein.atlant.dagger2.component.MainActivityComponent;
import com.frostchein.atlant.dagger2.modules.MainActivityModule;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView {

  @Inject
  MainPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    presenter.onCreate();
  }

  @Override
  public void initUI() {

  }

  @Override
  public void onStartSelected() {
    goToLoginSelectedActivity(true);
  }

  @Override
  public void onStartSelectedApp() {
    goToLoginSelectedAppActivity(true);
  }

  @Override
  public void onStartAuthorisation() {
    goToLoginActivity(true, LoginActivity.TYPE_AUTHORISATION);
  }

  @Override
  public void onStartHome() {
    goToHomeActivity(true);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    MainActivityComponent component = DaggerMainActivityComponent.builder()
        .appComponent(appComponent)
        .mainActivityModule(new MainActivityModule(this))
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
