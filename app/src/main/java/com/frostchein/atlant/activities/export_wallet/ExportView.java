package com.frostchein.atlant.activities.export_wallet;

import android.graphics.Bitmap;
import com.frostchein.atlant.activities.base.BaseView;

public interface ExportView extends BaseView {

  void setQR(Bitmap bitmap);

  void setPrivateKey(String privateKey);

}
