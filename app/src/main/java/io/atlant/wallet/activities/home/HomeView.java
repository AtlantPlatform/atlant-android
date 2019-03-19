/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.home;

import io.atlant.wallet.activities.base.BaseView;
import io.atlant.wallet.model.Balance;
import java.util.ArrayList;

public interface HomeView extends BaseView {

  void setContentOnToolbar(Balance balance);

  void setTransactionsOnFragment(ArrayList<Object> arrayList, int[] pointChart);

  void setNoTransactionsOnView(int[] pointChart);

  void onLoadingError();

  void onTimeout();

  void onNoInternetConnection();

  void onStartActivityBackup();

}
