package com.frostchein.atlant.activities.rent_details;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.LayoutParams;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.adapters.RentImagePagerAdapter;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerRentDetailsActivityComponent;
import com.frostchein.atlant.dagger2.component.RentDetailsActivityComponent;
import com.frostchein.atlant.dagger2.modules.RentDetailsActivityModule;
import com.frostchein.atlant.model.rent.Rent;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DialogUtils;
import com.frostchein.atlant.utils.DimensUtils;
import com.frostchein.atlant.utils.IntentUtils.EXTRA_STRING;
import com.frostchein.atlant.utils.ScrollUtils;
import com.frostchein.atlant.views.IndicatorCircleView;
import com.frostchein.atlant.views.LikeView;
import com.frostchein.atlant.views.MapView;
import java.util.ArrayList;
import javax.inject.Inject;

public class RentDetailsActivity extends BaseActivity implements RentDetailsView, MapView.CallBack {

  @Inject
  RentDetailsPresenter presenter;

  @BindView(R.id.image_fon)
  ImageView imageView;
  @BindView(R.id.scrollView)
  NestedScrollView nestedScrollView;

  @BindView(R.id.rent_detail_viewpager)
  ViewPager viewPager;
  @BindView(R.id.rent_detail_viewpager_linear)
  LinearLayout linearViewPager;

  @BindView(R.id.rent_detail__like_view)
  LikeView likeView;
  @BindView(R.id.rent_detail_bt_down)
  Button btLinearDown;
  @BindView(R.id.rent_detail_horizontalscroll_address)
  HorizontalScrollView horizontalScrollAddress;
  @BindView(R.id.rent_detail_linear_address)
  LinearLayout linearAddress;
  @BindView(R.id.rent_detail_name)
  TextView textLinearName;
  @BindView(R.id.rent_detail_address_text)
  TextView textLinearAddress;
  @BindView(R.id.rent_detail_rooms_text)
  TextView textLinearRooms;
  @BindView(R.id.rent_detail_beds_text)
  TextView textLinearBeds;

  @BindView(R.id.rent_detail_content_like_view)
  LikeView likeViewContent;
  @BindView(R.id.rent_detail_content_indicator)
  IndicatorCircleView indicatorCircleView;
  @BindView(R.id.rent_detail_content_name)
  TextView textContentName;
  @BindView(R.id.rent_detail_content_address)
  TextView textContentAddress;
  @BindView(R.id.rent_detail_content_description)
  TextView textContentDescription;
  @BindView(R.id.rent_detail_content_rooms)
  TextView textContentRooms;
  @BindView(R.id.rent_detail_content_guests)
  TextView textContentGuests;
  @BindView(R.id.rent_detail_content_beds)
  TextView textContentBeds;
  @BindView(R.id.rent_detail_content_bath)
  TextView textContentBath;
  @BindView(R.id.rent_detail_content_location_address)
  TextView textContentLocationAddress;
  @BindView(R.id.rent_detail_content_amenities_elevator_linear)
  LinearLayout linearElevator;
  @BindView(R.id.rent_detail_content_amenities_wifi_linear)
  LinearLayout linearWifi;
  @BindView(R.id.rent_detail_content_amenities_tv_linear)
  LinearLayout linearTV;
  @BindView(R.id.rent_detail_content_amenities_kitchen_linear)
  LinearLayout linearKitchen;


  @BindView(R.id.rent_detail_view_footer)
  View viewFooter;
  @BindView(R.id.rent_detail_linear_footer)
  LinearLayout linearFooter;
  @BindView(R.id.rent_detail_how_day_text)
  TextView textHowDay;
  @BindView(R.id.rent_detail_price_text)
  TextView textPrice;

  @BindView(R.id.rent_detail_content_map_view)
  MapView mapView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rent_detail);
    setToolbarTitle(R.string.rent_title);
    presenter.onCreate((Rent) getIntent().getParcelableExtra(EXTRA_STRING.RENT));
    // enableScrollToolbar(LayoutParams.SCROLL_FLAG_SCROLL);
    enableScrollToolbar(LayoutParams.SCROLL_FLAG_SCROLL | LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
  }

  @Override
  public void initUI() {
    CoordinatorLayout.LayoutParams coordinatorLayoutParams = (CoordinatorLayout.LayoutParams) swipeRefreshLayout
        .getLayoutParams();
    coordinatorLayoutParams.setBehavior(null);
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        if (ScrollUtils.canScroll(horizontalScrollAddress)) {
          linearAddress.setOrientation(LinearLayout.VERTICAL);
        }
      }
    }, 1);
    mapView.disableNestedScrollView(nestedScrollView);
    mapView.setCallBack(this);
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    RentDetailsActivityComponent component = DaggerRentDetailsActivityComponent.builder()
        .appComponent(appComponent)
        .rentDetailsActivityModule(new RentDetailsActivityModule(this))
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
  public void setName(String name) {
    textLinearName.setText(name);
    textContentName.setText(name);
  }

  @Override
  public void setAddress(String address) {
    textLinearAddress.setText(address);
    textContentAddress.setText(address);
  }

  @Override
  public void setRooms(String text) {
    textLinearRooms.setText(text);
    textContentRooms.setText(text);
  }

  @Override
  public void setGuests(String text) {
    textContentGuests.setText(text);
  }

  @Override
  public void setBeds(String text) {
    textLinearBeds.setText(text);
    textContentBeds.setText(text);
  }

  @Override
  public void setBath(String text) {
    textContentBath.setText(text);
  }

  @Override
  public void setLikes(int number) {
    likeView.setLike(number);
    likeViewContent.setLike(number);
  }

  @Override
  public void setUrlImages(ArrayList<String> arrayListURL) {
    viewPager.setAdapter(new RentImagePagerAdapter(getContext(), arrayListURL));
    viewPager.setPageMargin(DimensUtils.dpToPx(getContext(), 4));
    indicatorCircleView.setViewPager(viewPager);
  }

  @Override
  public void setHowDay(String text) {
    textHowDay.setText(text);
  }

  @Override
  public void setPrice(String price) {
    textPrice.setText(price);
  }

  @Override
  public void setCoordinates(double var1, double var2) {
    mapView.setLatLng(var1, var2);
  }

  @Override
  public void setAlphaFooter(float alpha) {
    viewFooter.setAlpha(alpha);
  }

  @Override
  public void setDescription(String text) {
    textContentDescription.setText(text);
  }

  @OnClick(R.id.rent_detail_bt_down)
  public void onClickAnimation() {
    presenter.startAnimationDown();
  }

  @OnClick(R.id.rent_detail_book_it)
  public void onClickBookIt() {
    DialogUtils.openDialogRentBookIt(getContext(), getString(R.string.rent_detail_dialog_message), null);
  }

  @Override
  public ViewPager getViewPager() {
    return viewPager;
  }

  public LinearLayout getLinearViewPager() {
    return linearViewPager;
  }

  @Override
  public int getSizeFooter() {
    return viewFooter.getHeight();
  }

  public NestedScrollView getNestedScrollView() {
    return nestedScrollView;
  }

  @Override
  public AppBarLayout getAppBarLayout() {
    return appBarLayout;
  }

  @Override
  public void startAnimation() {
    likeViewContent.setLike(likeView.getLike());
  }

  @Override
  public void removeAmenitiesTV() {
    ((ViewGroup) linearTV.getParent()).removeView(linearTV);
  }

  @Override
  public void removeAmenitiesElevator() {
    ((ViewGroup) linearElevator.getParent()).removeView(linearElevator);
  }

  @Override
  public void removeAmenitiesWiFi() {
    ((ViewGroup) linearWifi.getParent()).removeView(linearWifi);
  }

  @Override
  public void removeAmenitiesKitchen() {
    ((ViewGroup) linearKitchen.getParent()).removeView(linearKitchen);
  }

  @Override
  public void getAddress(String address) {
    textContentLocationAddress.setText(address);
    //temp
    textContentLocationAddress.setText(textContentAddress.getText());
  }
}
