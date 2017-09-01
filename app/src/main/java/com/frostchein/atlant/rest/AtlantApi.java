package com.frostchein.atlant.rest;

import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.GasPrice;
import com.frostchein.atlant.model.Nonce;
import com.frostchein.atlant.model.SendTransactions;
import com.frostchein.atlant.model.Transactions;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AtlantApi {

  @GET("api")
  Call<Balance> getWalletBalance(
      @Query("module") String module,
      @Query("action") String action,
      @Query("contractaddress") String contractAddress,
      @Query("address") String address,
      @Query("apikey") String apiKey
  );

  @GET("api")
  Call<Transactions> getWalletTransactionsIn(
      @Query("module") String module,
      @Query("action") String action,
      @Query("fromBlock") int fromBlock,
      @Query("toBlock") String toBlock,
      @Query("address") String contractAddress,
      @Query("topic2") String topic,
      @Query("apikey") String apiKey
  );

  @GET("api")
  Call<Transactions> getWalletTransactionsOut(
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
