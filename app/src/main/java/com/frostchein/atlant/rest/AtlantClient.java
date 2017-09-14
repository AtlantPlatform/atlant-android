package com.frostchein.atlant.rest;

import android.support.annotation.NonNull;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.GasPrice;
import com.frostchein.atlant.model.Nonce;
import com.frostchein.atlant.model.SendTransactions;
import com.frostchein.atlant.model.Transactions;
import com.frostchein.atlant.model.TransactionsTokens;
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
        .getTransactions("account", "txlist", address, "desc", Config.API_KEY_ETHERSCAN);
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
