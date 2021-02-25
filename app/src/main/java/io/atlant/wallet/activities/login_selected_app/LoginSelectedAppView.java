/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.login_selected_app;

import io.atlant.wallet.activities.base.BaseView;
import io.atlant.wallet.adapters.SelectedAppPagerAdapter;

public interface LoginSelectedAppView extends BaseView {

  void setAdapter(SelectedAppPagerAdapter adapter);

  void startWallet();

  void startRentals();

  void startTrade();

}
