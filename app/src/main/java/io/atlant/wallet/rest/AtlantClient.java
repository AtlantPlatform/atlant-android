/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
 */

package io.atlant.wallet.rest;

import android.support.annotation.NonNull;
import io.atlant.wallet.Config;
import io.atlant.wallet.model.Balance;
import io.atlant.wallet.model.GasPrice;
import io.atlant.wallet.model.Nonce;
import io.atlant.wallet.model.SendTransactions;
import io.atlant.wallet.model.Transactions;
import io.atlant.wallet.model.TransactionsTokens;

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
