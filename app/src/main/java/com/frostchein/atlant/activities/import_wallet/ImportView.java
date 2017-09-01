package com.frostchein.atlant.activities.import_wallet;

import com.frostchein.atlant.activities.base.BaseView;

public interface ImportView extends BaseView {

  String getPrivateKey();

  void stateBtContinue(boolean state);

}
