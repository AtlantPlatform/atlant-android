package com.frostchein.atlant.rest;

import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.events.network.OnStatusSuccess;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.Transactions;
import com.frostchein.atlant.model.TransactionsTokens;
import com.frostchein.atlant.model.TransactionsTokensItem;
import com.frostchein.atlant.utils.DigitsUtils;
import com.frostchein.atlant.utils.tokens.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Response;

public final class WalletRestHandler {

  private static Call<Balance> callBalance;
  private static Call<Transactions> callTransactions;
  private static Call<TransactionsTokens> callTransactionsTokens;

  public static void requestWalletInfo(AtlantClient atlantClient, String address, Token token) {
    requestBalance(atlantClient, address, token);
  }

  public static void requestWalletInfo(AtlantClient atlantClient, String address) {
    requestBalance(atlantClient, address, null);
  }

  public static void cancel() {
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
  }

  private static void requestBalance(
      final AtlantClient atlantClient,
      final String address,
      final Token token) {

    BaseRequest<Balance> callback = new BaseRequest<>(new BaseRequest.Callback<Balance>() {
      @Override
      public void onResponse(Response<Balance> response) {
        if (token == null) {
          requestTransactions(atlantClient, address, response.body());
        } else {
          requestTokenTransactions(atlantClient, address, token, response.body(), true, null);
        }
      }
    }, BaseActivity.REQUEST_CODE_HOME);

    if (token == null) {
      callBalance = atlantClient.getBalance(callback, address);
    } else {
      callBalance = atlantClient.getTokenBalance(callback, address, token.getContractAddress());
    }
  }

  private static void requestTokenTransactions(
      final AtlantClient atlantClient,
      final String address,
      final Token token,
      final Balance balance,
      final boolean transactionIn,
      final ArrayList<TransactionsTokensItem> transactionsTokensItems) {

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
                  requestTokenTransactions(atlantClient, address, token, balance, false, listIn);
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

                  TransactionsTokens transactionsTokens = response.body();
                  transactionsTokens.setTransactionsTokensItems(transactionsTokensItems);
                  EventBus.getDefault()
                      .post(new OnStatusSuccess(BaseActivity.REQUEST_CODE_HOME, balance, transactionsTokens));
                }
              });
              thread.start();
            }
          }
        }, BaseActivity.REQUEST_CODE_HOME);

    callTransactionsTokens = atlantClient
        .getTokenTransactions(callback, token.getContractAddress(), address, transactionIn);
  }

  private static void requestTransactions(
      final AtlantClient atlantClient,
      final String address,
      final Balance balance) {

    BaseRequest<Transactions> callback = new BaseRequest<>(new BaseRequest.Callback<Transactions>() {
      @Override
      public void onResponse(Response<Transactions> response) {
        EventBus.getDefault().post(new OnStatusSuccess(BaseActivity.REQUEST_CODE_HOME, balance, response.body()));
      }
    }, BaseActivity.REQUEST_CODE_HOME);

    callTransactions = atlantClient.getTransactions(callback, address);
  }
}
