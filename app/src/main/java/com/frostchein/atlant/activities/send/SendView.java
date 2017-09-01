package com.frostchein.atlant.activities.send;

import com.frostchein.atlant.activities.base.BaseView;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.Transactions;

public interface SendView extends BaseView {

  void setAddress(String address);

  void setValue(String value);

  void setBalance(Balance balance);

  String getAddress();

  String getValue();

  void onFormatMoney();

  void onInvalidAddress();

  void onNoMoney();

  void dialogConfirmTransaction();

  void onSuccessfulSend(Transactions transactions);

  void onError(String message);

  void onTimeout();

}
