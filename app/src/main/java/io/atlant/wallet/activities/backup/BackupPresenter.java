/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.backup;

import io.atlant.wallet.activities.base.BasePresenter;

public interface BackupPresenter extends BasePresenter {

  void onCreate();

  void onDone();

  void checked(boolean bol);

}
