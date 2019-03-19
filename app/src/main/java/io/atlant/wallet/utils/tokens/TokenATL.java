/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils.tokens;

import io.atlant.wallet.Config;

public class TokenATL extends BaseToken {

  @Override
  public String getName() {
    return Config.TOKEN_ATL_NAME;
  }

  @Override
  public String getContractAddress() {
    return Config.TOKEN_ATL_ADDRESS;
  }

  @Override
  public long getContractId() {
    return Config.TOKEN_ATL_ID;
  }
}
