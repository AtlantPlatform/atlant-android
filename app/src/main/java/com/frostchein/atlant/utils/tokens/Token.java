package com.frostchein.atlant.utils.tokens;

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
