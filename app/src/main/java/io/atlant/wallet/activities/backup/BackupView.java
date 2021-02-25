/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.backup;

import android.graphics.Bitmap;
import io.atlant.wallet.activities.base.BaseView;

public interface BackupView extends BaseView {

  void setQR(Bitmap bitmap);

  void setPrivateKey(String privateKey);

  void stateBtContinue(boolean state);

  void onFinish();

}
