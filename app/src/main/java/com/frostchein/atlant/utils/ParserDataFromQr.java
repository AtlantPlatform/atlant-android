package com.frostchein.atlant.utils;

import org.web3j.crypto.WalletUtils;

public class ParserDataFromQr {

  private static final String TAG_NAME_COIN = "atlant:";
  private static final String TAG_NAME_AMOUNT = "amount=";
  private String lineFromQr;


  public ParserDataFromQr(String lineFromQr) {
    this.lineFromQr = lineFromQr;
  }

  public boolean isCorrect() {
    return (getAddress() != null && getAmount() != null) || WalletUtils.isValidAddress(lineFromQr);
  }

  public String getAddress() {
    try {
      int n1 = lineFromQr.indexOf(TAG_NAME_COIN) + TAG_NAME_COIN.length();
      int n2 = lineFromQr.indexOf("?");
      String address = lineFromQr.substring(n1, n2);
      if (WalletUtils.isValidAddress(address)) {
        return address;
      }
    } catch (Exception ignored) {
      if (WalletUtils.isValidAddress(lineFromQr)) {
        return lineFromQr;
      }
    }
    return null;
  }

  public String getAmount() {
    try {
      int n1 = lineFromQr.indexOf(TAG_NAME_AMOUNT) + TAG_NAME_AMOUNT.length();
      int n2 = lineFromQr.length();
      String str = lineFromQr.substring(n1, n2);
      double d = Double.parseDouble(str);
      return str;
    } catch (Exception ignored) {

    }
    return null;
  }

  public static String generationLine(String address, double value) {
    return TAG_NAME_COIN + address + "?" + TAG_NAME_AMOUNT + String.valueOf(value);
  }
}
