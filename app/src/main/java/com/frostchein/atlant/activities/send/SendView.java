package com.frostchein.atlant.activities.send;

import com.frostchein.atlant.activities.base.BaseView;
import com.frostchein.atlant.model.Balance;

public interface SendView extends BaseView {

  void setAddress(String address);

  void setValue(String value);

  void setBalance(Balance balance);

  void setType(String walletName);

  String getAddress();

  String getValue();

  void onFormatMoney();

  void onInvalidAddress();

  void onNoMoney();

  void dialogConfirmTransaction();

  void onSuccessfulSend();

  void onError(String message);

  void onTimeout();

}
