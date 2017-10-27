package com.frostchein.atlant.activities.login_selected_app;

import android.content.Context;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.adapters.SelectedAppPagerAdapter;
import com.frostchein.atlant.adapters.SelectedAppPagerAdapter.CallBack;
import com.frostchein.atlant.model.SelectedApp;
import java.util.ArrayList;
import javax.inject.Inject;

public class LoginSelectedAppPresenterImpl implements LoginSelectedAppPresenter, BasePresenter, CallBack {

  private LoginSelectedAppView view;

  @Inject
  LoginSelectedAppPresenterImpl(LoginSelectedAppView view) {
    this.view = view;
  }

  @Override
  public void onCreate() {
    Context context = view.getContext();
    String url1 = "https://atlant.io/promo/android/1.png";
    String url2 = "https://atlant.io/promo/android/3.png";
    String url3 = "https://atlant.io/promo/android/2.png";

    ArrayList<SelectedApp> arrayListTitle = new ArrayList<>();

    arrayListTitle.add(new SelectedApp(context.getString(R.string.login_selected_app_invest_title),
        context.getString(R.string.login_selected_app_invest_title2), url1));

    arrayListTitle.add(new SelectedApp(context.getString(R.string.login_selected_app_rent_title),
        context.getString(R.string.login_selected_app_rent_title2), url2));

    arrayListTitle.add(new SelectedApp(context.getString(R.string.login_selected_app_trade_title),
        context.getString(R.string.login_selected_app_trade_title2), url3));

    SelectedAppPagerAdapter adapter = new SelectedAppPagerAdapter(view.getContext(), arrayListTitle);
    adapter.setCallBack(this);
    view.setAdapter(adapter);
  }

  @Override
  public void onClickItems(int pos) {

    if (pos == 0) {
      view.startWallet();
    }
    if (pos == 1) {
      view.startRentals();
    }
    if (pos == 2) {
      view.startTrade();
    }
  }
}

