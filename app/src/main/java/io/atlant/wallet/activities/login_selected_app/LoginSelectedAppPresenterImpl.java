/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.login_selected_app;

import android.content.Context;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BasePresenter;
import io.atlant.wallet.adapters.SelectedAppPagerAdapter;
import io.atlant.wallet.adapters.SelectedAppPagerAdapter.CallBack;
import io.atlant.wallet.model.SelectedApp;
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
    int url1 = R.drawable.start_wallet;
    int url2 = R.drawable.start_rent;
    int url3 = R.drawable.start_exchange;

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

