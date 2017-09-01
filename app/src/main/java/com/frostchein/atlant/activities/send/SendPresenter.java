package com.frostchein.atlant.activities.send;

import com.frostchein.atlant.activities.base.BasePresenter;

public interface SendPresenter extends BasePresenter {

  void onCreate(String line);

  void onValidate();

  void onSendTransaction();

}
