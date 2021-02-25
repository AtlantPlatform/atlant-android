/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.home;

import android.os.Bundle;
import io.atlant.wallet.activities.base.BasePresenter;

public interface HomePresenter extends BasePresenter {

  void onCreate(Bundle savedInstanceState);

  void onChangeValue(int pos);

  void onUpdateLocal();

  void refreshContent();

}
