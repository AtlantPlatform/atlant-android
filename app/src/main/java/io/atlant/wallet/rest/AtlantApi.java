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
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AtlantApi {

  @GET("api")
  Call<Balance> getBalance(
      @Query("module") String module,
      @Query("action") String action,
      @Query("contractaddress") String contractAddress,
      @Query("address") String address,
      @Query("apikey") String apiKey
  );

  @GET("api")
  Call<Transactions> getTransactions(
      @Query("module") String module,
      @Query("action") String action,
      @Query("address") String address,
      @Query("page") int page,
      @Query("offset") int offset,
      @Query("sort") String sort,
      @Query("apikey") String apiKey
  );

  @GET("api")
  Call<TransactionsTokens> getTokenTransactionsIn(
      @Query("module") String module,
      @Query("action") String action,
      @Query("fromBlock") int fromBlock,
      @Query("toBlock") String toBlock,
      @Query("address") String contractAddress,
      @Query("topic2") String topic,
      @Query("apikey") String apiKey
  );

  @GET("api")
  Call<TransactionsTokens> getTokenTransactionsOut(
      @Query("module") String module,
      @Query("action") String action,
      @Query("fromBlock") int fromBlock,
      @Query("toBlock") String toBlock,
      @Query("address") String contractAddress,
      @Query("topic1") String topic,
      @Query("apikey") String apiKey
  );

  @GET("api")
  Call<GasPrice> getGasPrice(
      @Query("module") String module,
      @Query("action") String action,
      @Query("apikey") String apiKey
  );

  @GET("api")
  Call<Nonce> getNonce(
      @Query("module") String module,
      @Query("action") String action,
      @Query("address") String address,
      @Query("tag") String tag,
      @Query("apikey") String apiKey
  );

  @GET("api")
  Call<SendTransactions> sendTransaction(
      @Query("module") String module,
      @Query("action") String action,
      @Query("hex") String address,
      @Query("apikey") String apiKey
  );
}