package com.frostchein.atlant.activities.camera;

import com.frostchein.atlant.activities.base.BaseView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public interface CameraView extends BaseView {

  void scannerView(ZXingScannerView scannerView);

  void onSuccessfulScanQR(String result);

  void onFailScanQR();
}
