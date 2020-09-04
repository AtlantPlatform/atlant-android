/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.rest;

import androidx.annotation.NonNull;
import io.atlant.wallet.Config;
import io.atlant.wallet.model.Balance;
import io.atlant.wallet.model.GasPrice;
import io.atlant.wallet.model.Nonce;
import io.atlant.wallet.model.SendTransactions;
import io.atlant.wallet.model.Transactions;
import io.atlant.wallet.model.TransactionsTokens;

import retrofit2.Call;
import retrofit2.Callback;

public class AtlantClient {

  private AtlantApi atlantApi;

  public AtlantClient(AtlantApi atlantApi) {
    this.atlantApi = atlantApi;
  }

  Call<Balance> getBalance(@NonNull Callback<Balance> walletCallback, String address) {
    Call<Balance> walletCall = atlantApi
        .getBalance("account", "balance", null, address, Config.API_KEY_ETHERSCAN);
    walletCall.enqueue(walletCallback);
    return walletCall;
  }

  Call<Balance> getTokenBalance(@NonNull Callback<Balance> walletCallback, String address, String contractAddress) {
    Call<Balance> walletCall = atlantApi
        .getBalance("account", "tokenbalance", contractAddress, address, Config.API_KEY_ETHERSCAN);
    walletCall.enqueue(walletCallback);
    return walletCall;
  }

  Call<Transactions> getTransactions(@NonNull Callback<Transactions> walletCallback, String address) {
    Call<Transactions> walletCall = atlantApi
        .getTransactions("account", "txlist", address, 1, Config.MAX_TRANSACTIONS, "desc", Config.API_KEY_ETHERSCAN);
    walletCall.enqueue(walletCallback);
    return walletCall;
  }

  Call<TransactionsTokens> getTokenTransactions(@NonNull Callback<TransactionsTokens> walletCallback,
                                                String contractAddress,
                                                String address,
                                                boolean transactionIn) {
    if (transactionIn) {
      Call<TransactionsTokens> walletCall = atlantApi
          .getTokenTransactionsIn("logs", "getLogs", 0, "latest", contractAddress, formatAddress(address),
              Config.API_KEY_ETHERSCAN);
      walletCall.enqueue(walletCallback);
      return walletCall;
    } else {
      Call<TransactionsTokens> walletCall = atlantApi
          .getTokenTransactionsOut("logs", "getLogs", 0, "latest", contractAddress, formatAddress(address),
              Config.API_KEY_ETHERSCAN);
      walletCall.enqueue(walletCallback);
      return walletCall;
    }
  }

  Call<GasPrice> getGasPrice(@NonNull Callback<GasPrice> callback) {
    Call<GasPrice> priceCall = atlantApi.getGasPrice("proxy", "eth_gasPrice", Config.API_KEY_ETHERSCAN);
    priceCall.enqueue(callback);
    return priceCall;
  }

  Call<Nonce> getNonce(@NonNull Callback<Nonce> callback, String address) {
    Call<Nonce> nonceCall = atlantApi
        .getNonce("proxy", "eth_getTransactionCount", address, "latest", Config.API_KEY_ETHERSCAN);
    nonceCall.enqueue(callback);
    return nonceCall;
  }

  Call<SendTransactions> sendTransaction(@NonNull Callback<SendTransactions> callback, String hex) {
    Call<SendTransactions> sendTransactionCall = atlantApi
        .sendTransaction("proxy", "eth_sendRawTransaction", hex, Config.API_KEY_ETHERSCAN);
    sendTransactionCall.enqueue(callback);
    return sendTransactionCall;
  }

  private String formatAddress(String address) {
    StringBuilder sb = new StringBuilder(address);
    sb.insert(2, "000000000000000000000000");
    return sb.toString();
  }

}
