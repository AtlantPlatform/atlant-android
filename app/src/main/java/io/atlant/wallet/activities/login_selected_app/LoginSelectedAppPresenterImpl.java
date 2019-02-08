/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
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
