package com.frostchein.atlant.activities.rent_main;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout.LayoutParams;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.adapters.RentAdapter;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerRentMainActivityComponent;
import com.frostchein.atlant.dagger2.component.RentMainActivityComponent;
import com.frostchein.atlant.dagger2.modules.RentMainActivityModule;
import com.frostchein.atlant.model.rent.Rent;
import com.frostchein.atlant.model.rent.RentCity;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.views.BaseCustomView;
import com.frostchein.atlant.views.ToolbarRentView;
import java.util.ArrayList;
import javax.inject.Inject;

public class RentMainActivity extends BaseActivity implements RentMainView, ToolbarRentView.CallBack,
    RentAdapter.CallBack {

  @Inject
  RentMainPresenter presenter;
  @Inject
  ToolbarRentView toolbarRentView;

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.rate_bt_filter1)
  Button btFilter1;
  @BindView(R.id.rate_bt_filter2)
  Button btFilter2;
  @BindView(R.id.rate_bt_filter3)
  Button btFilter3;
  @BindView(R.id.rate_bt_filter4)
  Button btFilter4;
  @BindView(R.id.rate_bt_filter5)
  Button btFilter5;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rent_main);
    setToolbarTitle(R.string.rent_title);
    presenter.onCreate();
    enableScrollToolbar();
  }

  @Override
  public void onResume() {
    super.onResume();
    toolbarRentView.setCallback(this);
  }

  @Override
  public void onPause() {
    toolbarRentView.setCallback(null);
    super.onPause();
  }

  @Override
  public void initUI() {

  }

  @OnClick(R.id.rate_bt_filter1)
  public void onClickFilter1() {
    presenter.setCategory(1);
  }

  @OnClick(R.id.rate_bt_filter2)
  public void onClickFilter2() {
    presenter.setCategory(2);
  }

  @OnClick(R.id.rate_bt_filter3)
  public void onClickFilter3() {
    presenter.setCategory(3);
  }

  @OnClick(R.id.rate_bt_filter4)
  public void onClickFilter4() {
    presenter.setCategory(4);
  }

  @OnClick(R.id.rate_bt_filter5)
  public void onClickFilter5() {
    presenter.setCategory(5);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    RentMainActivityComponent component = DaggerRentMainActivityComponent.builder()
        .appComponent(appComponent)
        .rentMainActivityModule(new RentMainActivityModule(this))
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
    return true;
  }

  @Override
  public boolean useSwipeRefresh() {
    return true;
  }

  @Override
  public boolean timerLogOut() {
    return CredentialHolder.isLogged();
  }

  @Override
  protected BaseCustomView getCustomToolbar() {
    return toolbarRentView;
  }

  @Override
  protected void onRefreshSwipe() {
    presenter.onUpdate();
  }

  @Override
  public void setCategory(int numberCategory) {
    int drawableNormal = R.drawable.highlight;
    btFilter1.setBackgroundResource(drawableNormal);
    btFilter2.setBackgroundResource(drawableNormal);
    btFilter3.setBackgroundResource(drawableNormal);
    btFilter4.setBackgroundResource(drawableNormal);
    btFilter5.setBackgroundResource(drawableNormal);

    int drawablePress = R.drawable.highlight_bt_rate_category;
    switch (numberCategory) {
      case 1:
        btFilter1.setBackgroundResource(drawablePress);
        break;
      case 2:
        btFilter2.setBackgroundResource(drawablePress);
        break;
      case 3:
        btFilter3.setBackgroundResource(drawablePress);
        break;
      case 4:
        btFilter4.setBackgroundResource(drawablePress);
        break;
      case 5:
        btFilter5.setBackgroundResource(drawablePress);
        break;
    }
  }

  @Override
  public void setToolbar(ArrayList<RentCity> arrayList) {
    toolbarRentView.setContent(arrayList);
  }

  @Override
  public void update(ArrayList<Rent> arrayList) {
    RentAdapter adapter = new RentAdapter(arrayList);
    adapter.setCallBack(this);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);
    recyclerView.setHasFixedSize(true);
    recyclerView.setNestedScrollingEnabled(false);
  }

  @Override
  public void startActivity(Rent rent) {
    goToRentDetailsActivity(false, rent);
  }

  @Override
  public void onCityClick(int pos) {
    presenter.setCity(pos);
  }

  @Override
  public void onSelected(int pos) {
    presenter.onSelected(pos);
  }
}
