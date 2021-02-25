/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.base;

import android.content.Context;

public interface BaseView {

  Context getContext();

  void onRefreshStart();

  void onRefreshComplete();

  void onNoInternetConnection();

  void showProgressDialog(String dialogMessage);

  void hideProgressDialog();
}
