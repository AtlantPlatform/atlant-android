package com.frostchein.atlant.activities.login_selected_app;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import butterknife.BindView;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.adapters.SelectedAppPagerAdapter;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerLoginSelectedAppActivityComponent;
import com.frostchein.atlant.dagger2.component.LoginSelectedAppActivityComponent;
import com.frostchein.atlant.dagger2.modules.LoginSelectedAppActivityModule;
import com.frostchein.atlant.utils.AnimationUtils.AnimationSelectedAppViewPager;
import com.frostchein.atlant.utils.FontsUtils;
import com.frostchein.atlant.views.IndicatorCircleView;
import javax.inject.Inject;

public class LoginSelectedAppActivity extends BaseActivity implements LoginSelectedAppView {

  @BindView(R.id.name)
  TextView textName;
  @BindView(R.id.login_selected_app_viewpager)
  ViewPager viewPager;
  @BindView(R.id.login_selected_app_indicator)
  IndicatorCircleView indicatorCircleView;

  @Inject
  LoginSelectedAppPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_selected_app);
    FontsUtils.toOctarineBold(getContext(), textName);
    presenter.onCreate();
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
    LoginSelectedAppActivityComponent component = DaggerLoginSelectedAppActivityComponent.builder()
        .appComponent(appComponent)
        .loginSelectedAppActivityModule(new LoginSelectedAppActivityModule(this))
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

  @Override
  public void setAdapter(SelectedAppPagerAdapter adapter) {
    viewPager.setAdapter(adapter);
    viewPager.setPageTransformer(true, new AnimationSelectedAppViewPager());
    indicatorCircleView.setViewPagerCircle(viewPager);
  }

  @Override
  public void startWallet() {
    goToLoginSelectedActivity(false);
  }

  @Override
  public void startRentals() {
    String appPackageName = "io.atlant.rent";
    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appPackageName);
    if (launchIntent != null) {
      try {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName(appPackageName, appPackageName + ".activities.demo.DemoActivity"));
        startActivity(intent);
      } catch (Exception e) {
        e.printStackTrace();
        startActivity(launchIntent);
      }
    } else {
      String url = "https://play.google.com/store/apps/details?id=" + appPackageName;
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
  }

  @Override
  public void startTrade() {
    showMessage(getString(R.string.system_section_in_development));
  }

}
