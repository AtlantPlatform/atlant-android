package com.frostchein.atlant.rest;

import android.support.annotation.NonNull;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.GasPrice;
import com.frostchein.atlant.model.Nonce;
import com.frostchein.atlant.model.SendTransactions;
import com.frostchein.atlant.model.Transactions;
import retrofit2.Call;
import retrofit2.Callback;

public class AtlantClient {

  private AtlantApi atlantApi;

  public AtlantClient(AtlantApi atlantApi) {
    this.atlantApi = atlantApi;
  }

  void getWalletBalance(@NonNull Callback<Balance> walletCallback, String address) {
    Call<Balance> walletCall = atlantApi
        .getWalletBalance("account", "tokenbalance", Config.CONTRACT_ADDRESS, address, Config.API_KEY_ETHERSCAN);
    walletCall.enqueue(walletCallback);
  }

  void getWalletTransactions(@NonNull Callback<Transactions> walletCallback, String address, boolean transactionIn) {
    if (transactionIn) {
      Call<Transactions> walletCall = atlantApi
          .getWalletTransactionsIn("logs", "getLogs", 0, "latest", Config.CONTRACT_ADDRESS, formatAddress(address),
              Config.API_KEY_ETHERSCAN);
      walletCall.enqueue(walletCallback);
    } else {
      Call<Transactions> walletCall = atlantApi
          .getWalletTransactionsOut("logs", "getLogs", 0, "latest", Config.CONTRACT_ADDRESS, formatAddress(address),
              Config.API_KEY_ETHERSCAN);
      walletCall.enqueue(walletCallback);
    }
  }

  void getGasPrice(@NonNull Callback<GasPrice> callback) {
    Call<GasPrice> priceCall = atlantApi.getGasPrice("proxy", "eth_gasPrice", Config.API_KEY_ETHERSCAN);
    priceCall.enqueue(callback);
  }

  void getNonce(@NonNull Callback<Nonce> callback, String address) {
    Call<Nonce> nonceCall = atlantApi
        .getNonce("proxy", "eth_getTransactionCount", address, "latest", Config.API_KEY_ETHERSCAN);
    nonceCall.enqueue(callback);
  }

  void sendTransaction(@NonNull Callback<SendTransactions> callback, String hex) {
    Call<SendTransactions> nonceCall = atlantApi
        .sendTransaction("proxy", "eth_sendRawTransaction", hex, Config.API_KEY_ETHERSCAN);
    nonceCall.enqueue(callback);
  }

  private String formatAddress(String address) {
    StringBuilder sb = new StringBuilder(address);
    sb.insert(2, "000000000000000000000000");
    return sb.toString();
  }

}
