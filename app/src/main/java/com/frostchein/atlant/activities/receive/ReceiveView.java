package com.frostchein.atlant.activities.receive;

import android.graphics.Bitmap;
import com.frostchein.atlant.activities.base.BaseView;

public interface ReceiveView extends BaseView {

  void onKeyCopied();

  void setQR(Bitmap bitmap);

  void setAddress(String address);
}
