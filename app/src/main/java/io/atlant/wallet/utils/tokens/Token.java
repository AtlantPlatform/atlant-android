/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils.tokens;

public class Token {

  public String getName() {
    return null;
  }


  public String getContractAddress() {
    return null;
  }

  public long getContractId() {
    return 0L;
  }

  public String getNameFileBalance() {
    return getName().toLowerCase() + "_balance.data";
  }

  public String getNameFileTransactions() {
    return getName().toLowerCase() + "_transactions.data";
  }


}
