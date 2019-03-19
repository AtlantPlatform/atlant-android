/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.import_wallet;

import io.atlant.wallet.activities.base.BaseView;

public interface ImportView extends BaseView {

  String getPrivateKey();

  void stateBtContinue(boolean state);

}
