/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.receive;

import android.graphics.Bitmap;
import io.atlant.wallet.activities.base.BaseView;

public interface ReceiveView extends BaseView {

  void onKeyCopied();

  void setQR(Bitmap bitmap);

  void setAddress(String address);
}
