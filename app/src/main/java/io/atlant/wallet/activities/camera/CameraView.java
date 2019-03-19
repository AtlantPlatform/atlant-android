/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.camera;

import io.atlant.wallet.activities.base.BaseView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public interface CameraView extends BaseView {

  void scannerView(ZXingScannerView scannerView);

  void onSuccessfulScanQR(String result);

  void onFailScanQR();
}
