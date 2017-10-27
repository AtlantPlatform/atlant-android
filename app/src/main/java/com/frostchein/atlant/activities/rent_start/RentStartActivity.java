package com.frostchein.atlant.activities.rent_start;

import android.os.Bundle;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerRentStartActivityComponent;
import com.frostchein.atlant.dagger2.component.RentStartActivityComponent;
import com.frostchein.atlant.dagger2.modules.RentStartActivityModule;
import com.frostchein.atlant.utils.CredentialHolder;
import javax.inject.Inject;

public class RentStartActivity extends BaseActivity implements RentStartView {

  @Inject
  RentStartPresenter presenter;

  @BindView(R.id.image_fon)
  ImageView imFon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rent_start);
    setToolbarTitle(R.string.rent_title);
    presenter.onCreate();
  }

  @Override
  public void initUI() {

  }

  @OnClick(R.id.bt_lets_start)
  public void onClick() {
    goToRentMainActivity(false);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    RentStartActivityComponent component = DaggerRentStartActivityComponent.builder()
        .appComponent(appComponent)
        .rentStartActivityModule(new RentStartActivityModule(this))
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
  public ImageView getImageView() {
    return imFon;
  }
}
