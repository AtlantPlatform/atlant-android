/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.send;

import io.atlant.wallet.activities.base.BaseView;
import io.atlant.wallet.model.Balance;

public interface SendView extends BaseView {

  void setAddress(String address);

  void setValue(String value);

  void setBalance(Balance balance);

  void setWalletName(String walletName);

  String getAddress();

  String getValue();

  void onFormatMoney();

  void onInvalidAddress();

  void onNoMoney();

  void dialogConfirmTransaction();

  void onSuccessfulSend();

  void onError(String message);

  void onTimeout();

  void setContentOnToolbar(Balance balance);

}
