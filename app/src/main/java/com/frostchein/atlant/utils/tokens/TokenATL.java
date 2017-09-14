package com.frostchein.atlant.utils.tokens;

import com.frostchein.atlant.Config;

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
