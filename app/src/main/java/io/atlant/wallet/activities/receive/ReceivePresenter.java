/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.receive;

import io.atlant.wallet.activities.base.BasePresenter;

public interface ReceivePresenter extends BasePresenter {

  void onCreated();

  void onCopyAddress();

}
