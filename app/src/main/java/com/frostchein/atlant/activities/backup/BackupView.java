package com.frostchein.atlant.activities.backup;

import android.graphics.Bitmap;
import com.frostchein.atlant.activities.base.BaseView;

public interface BackupView extends BaseView {

  void setQR(Bitmap bitmap);

  void setPrivateKey(String privateKey);

  void stateBtContinue(boolean state);

  void onFinish();

}
