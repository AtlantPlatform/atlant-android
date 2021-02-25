/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.rest;

import io.atlant.wallet.events.network.OnStatusSuccess;
import io.atlant.wallet.model.Balance;
import io.atlant.wallet.model.Transactions;
import io.atlant.wallet.model.TransactionsTokens;
import io.atlant.wallet.model.TransactionsTokensItem;
import io.atlant.wallet.utils.DigitsUtils;
import io.atlant.wallet.utils.tokens.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

import io.atlant.wallet.events.network.OnStatusSuccess;
import io.atlant.wallet.model.Balance;
import io.atlant.wallet.model.Transactions;
import io.atlant.wallet.model.TransactionsTokens;
import io.atlant.wallet.model.TransactionsTokensItem;
import io.atlant.wallet.utils.DigitsUtils;
import io.atlant.wallet.utils.tokens.Token;
import retrofit2.Call;
import retrofit2.Response;

final class WalletRestHandler {

  private Call<Balance> callBalance;
  private Call<Transactions> callTransactions;
  private Call<TransactionsTokens> callTransactionsTokens;

  private Balance balance;
  private Transactions transactions;
  private TransactionsTokens transactionsTokens;

  void requestWalletInfo(AtlantClient atlantClient, String address, Token token, int requestCode) {
    requestBalance(atlantClient, address, token, requestCode);
  }

  void requestWalletInfo(AtlantClient atlantClient, String address, int requestCode) {
    requestBalance(atlantClient, address, null, requestCode);
  }

  void cancel() {
    if (callBalance != null) {
      callBalance.cancel();
    }
    if (callTransactions != null) {
      callTransactions.cancel();
    }
    if (callTransactionsTokens != null) {
      callTransactionsTokens.cancel();
    }

    callBalance = null;
    callTransactions = null;
    callTransactionsTokens = null;
    balance = null;
    transactions = null;
    transactionsTokens = null;
  }

  private void requestBalance(
      final AtlantClient atlantClient,
      final String address,
      final Token token,
      final int requestCode) {

    BaseRequest<Balance> callback = new BaseRequest<>(new BaseRequest.Callback<Balance>() {
      @Override
      public void onResponse(Response<Balance> response) {
        balance = response.body();

        if (balance != null && (transactionsTokens != null || transactions != null)) {
          if (token == null) {
            EventBus.getDefault().post(new OnStatusSuccess(requestCode, balance, transactions, null));
          } else {
            EventBus.getDefault().post(new OnStatusSuccess(requestCode, balance, transactionsTokens, token));
          }
        }

      }
    }, requestCode);

    if (token == null) {
      callBalance = atlantClient.getBalance(callback, address);
    } else {
      callBalance = atlantClient.getTokenBalance(callback, address, token.getContractAddress());
    }
  }

  void requestTokenTransactions(
      final AtlantClient atlantClient,
      final String address,
      final Token token,
      final boolean transactionIn,
      final ArrayList<TransactionsTokensItem> transactionsTokensItems,
      final int requestCode) {

    BaseRequest<TransactionsTokens> callback = new BaseRequest<>(
        new BaseRequest.Callback<TransactionsTokens>() {
          @Override
          public void onResponse(final Response<TransactionsTokens> response) {
            if (transactionIn) {
              //GET TRANSACTIONS IN
              Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                  ArrayList<TransactionsTokensItem> listIn = response.body().getTransactionsTokensItems();
                  for (int i = 0; i < listIn.size(); i++) {
                    listIn.get(i).setTransactionsIn(true);
                  }
                  requestTokenTransactions(atlantClient, address, token, false, listIn, requestCode);
                }
              });
              thread.start();
            } else {
              //GET TRANSACTIONS OUT
              Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                  List<TransactionsTokensItem> listOut = response.body().getTransactionsTokensItems();
                  for (int i = 0; i < listOut.size(); i++) {
                    listOut.get(i).setTransactionsIn(false);
                  }
                  //ADD TRANSACTIONS
                  transactionsTokensItems.addAll(listOut);
                  //SORT TRANSACTIONS
                  Collections.sort(transactionsTokensItems, new Comparator<TransactionsTokensItem>() {
                    @Override
                    public int compare(TransactionsTokensItem t0, TransactionsTokensItem t1) {
                      return DigitsUtils.getBase10from16(t0.getTimeStamp()).longValue() > DigitsUtils
                          .getBase10from16(t1.getTimeStamp()).longValue() ? -1
                          : (DigitsUtils.getBase10from16(t0.getTimeStamp()).longValue() < DigitsUtils
                              .getBase10from16(t1.getTimeStamp()).longValue()
                          ) ? 1 : 0;
                    }
                  });

                  transactionsTokens = response.body();
                  transactionsTokens.setTransactionsTokensItems(transactionsTokensItems);

                  if (transactionsTokens != null && balance != null) {
                    EventBus.getDefault()
                        .post(new OnStatusSuccess(requestCode, balance, transactionsTokens, token));
                  }
                }
              });
              thread.start();
            }
          }
        }, requestCode);

    callTransactionsTokens = atlantClient
        .getTokenTransactions(callback, token.getContractAddress(), address, transactionIn);
  }

  void requestTransactions(
      final AtlantClient atlantClient,
      final String address,
      final int requestCode) {

    BaseRequest<Transactions> callback = new BaseRequest<>(new BaseRequest.Callback<Transactions>() {
      @Override
      public void onResponse(Response<Transactions> response) {
        transactions = response.body();
        if (transactions != null && balance != null) {
          EventBus.getDefault().post(new OnStatusSuccess(requestCode, balance, response.body(), null));
        }
      }
    }, requestCode);

    callTransactions = atlantClient.getTransactions(callback, address);
  }
}
