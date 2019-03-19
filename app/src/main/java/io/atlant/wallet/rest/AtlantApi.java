/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
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
