package com.frostchein.atlant.activities.receive;

import com.frostchein.atlant.activities.base.BasePresenter;

public interface ReceivePresenter extends BasePresenter {

  void onCreated();

  void onCopyAddress();

  void onGenerationQR(double value);
}
