/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet;

import atlant.wallet.BuildConfig;

public class Config {

  //NETWORK
  public static String ENDPOINT_URL = BuildConfig.BASE_URL;
  public static String API_KEY_ETHERSCAN = "K9NRZB3QX3WG9J5PWWHWXA3MDJZ18VCQJS";
  public static long GAS_LIMIT = 100000L;

  //ENCODE & DECODE
  public static int N_APPLICATION = 1024;

  //DIFFERENT
  //eth
  public static final String WALLET_ETH = "ETH";
  //atl
  public static final String TOKEN_ATL_NAME = "ATL";
  public static String TOKEN_ATL_ADDRESS = "0x78B7FADA55A64dD895D8c8c35779DD8b67fA8a05";
  public static long TOKEN_ATL_ID = 2835717307L;

  public static int SIZE_PX_QR = 500;
  public static int MAX_TRANSACTIONS = 500;

}
