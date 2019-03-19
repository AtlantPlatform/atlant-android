/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.export_wallet;

import android.graphics.Bitmap;
import io.atlant.wallet.activities.base.BaseView;

public interface ExportView extends BaseView {

  void setQR(Bitmap bitmap);

  void setPrivateKey(String privateKey);

}
