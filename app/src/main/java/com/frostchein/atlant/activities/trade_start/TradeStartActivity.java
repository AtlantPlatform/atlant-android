package com.frostchein.atlant.activities.trade_start;

import android.os.Bundle;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerTradeStartActivityComponent;
import com.frostchein.atlant.dagger2.component.TradeStartActivityComponent;
import com.frostchein.atlant.dagger2.modules.TradeStartActivityModule;
import com.frostchein.atlant.utils.CredentialHolder;
import javax.inject.Inject;

public class TradeStartActivity extends BaseActivity implements TradeStartView {

  @Inject
  TradeStartPresenter presenter;

  @BindView(R.id.image_fon)
  ImageView imFon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trade_start);
    setToolbarTitle(R.string.trade_title);
    presenter.onCreate();
  }

  @Override
  public void initUI() {

  }

  @OnClick(R.id.bt_done)
  public void onClick() {
    finish();
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    TradeStartActivityComponent component = DaggerTradeStartActivityComponent.builder()
        .appComponent(appComponent)
        .tradeStartActivityModule(new TradeStartActivityModule(this))
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
