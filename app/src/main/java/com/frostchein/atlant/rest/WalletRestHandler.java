package com.frostchein.atlant.rest;

import android.content.Context;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.events.network.OnStatusError;
import com.frostchein.atlant.events.network.OnStatusSuccess;
import com.frostchein.atlant.events.network.OnStatusTimeOut;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.TransactionItems;
import com.frostchein.atlant.model.Transactions;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DigitsUtils;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class WalletRestHandler {

  public static void requestWalletBalance(final Context context, final AtlantClient atlantClient) {
    try {
      atlantClient.getWalletBalance(new Callback<Balance>() {
        @Override
        public void onResponse(Call<Balance> call, Response<Balance> response) {
          if (response.isSuccessful()) {
            requestWalletTransactions(context, atlantClient, response.body(), true, null);
          } else {
            CredentialHolder.deleteWalletInfo(context);
            EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_HOME));
          }
        }

        @Override
        public void onFailure(Call<Balance> call, Throwable throwable) {
          if (throwable instanceof SocketTimeoutException) {
            EventBus.getDefault().post(new OnStatusTimeOut(BaseActivity.REQUEST_CODE_HOME));
          } else {
            EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_HOME));
          }
        }
      }, CredentialHolder.getAddress());
    } catch (Exception e) {
      EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_HOME));
    }
  }


  private static void requestWalletTransactions(final Context context, final AtlantClient atlantClient,
      final Balance balance, final boolean transactionIn, final ArrayList<TransactionItems> transactionItems) {
    try {
      atlantClient.getWalletTransactions(new Callback<Transactions>() {
        @Override
        public void onResponse(Call<Transactions> call, final Response<Transactions> response) {
          if (response.isSuccessful()) {
            if (transactionIn) {
              //GET TRANSACTIONS IN
              Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                  ArrayList<TransactionItems> listIn = response.body().getTransactionItems();
                  for (int i = 0; i < listIn.size(); i++) {
                    listIn.get(i).setTransactionsIn(transactionIn);
                  }
                  requestWalletTransactions(context, atlantClient, balance, false, listIn);
                }
              });
              thread.start();


            } else {
              //GET TRANSACTIONS OUT
              Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                  List<TransactionItems> listOut = response.body().getTransactionItems();
                  for (int i = 0; i < listOut.size(); i++) {
                    listOut.get(i).setTransactionsIn(transactionIn);
                  }
                  //ADD TRANSACTIONS
                  transactionItems.addAll(listOut);
                  //SORT TRANSACTIONS
                  Collections.sort(transactionItems, new Comparator<TransactionItems>() {
                    @Override
                    public int compare(TransactionItems t0, TransactionItems t1) {
                      return DigitsUtils.getBase10from16(t0.getTimeStamp()).longValue() > DigitsUtils
                          .getBase10from16(t1.getTimeStamp()).longValue() ? -1
                          : (DigitsUtils.getBase10from16(t0.getTimeStamp()).longValue() < DigitsUtils
                              .getBase10from16(t1.getTimeStamp()).longValue()
                          ) ? 1 : 0;
                    }
                  });

                  Transactions transactions = response.body();
                  transactions.setTransactionItems(transactionItems);
                  CredentialHolder.saveWalletInfo(context, balance, transactions);
                  EventBus.getDefault()
                      .post(new OnStatusSuccess(BaseActivity.REQUEST_CODE_HOME, balance, transactions));
                }
              });
              thread.start();
            }
          } else {
            CredentialHolder.deleteWalletInfo(context);
            EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_HOME));
          }
        }

        @Override
        public void onFailure(Call<Transactions> call, Throwable throwable) {
          if (throwable instanceof SocketTimeoutException) {
            EventBus.getDefault().post(new OnStatusTimeOut(BaseActivity.REQUEST_CODE_HOME));
          } else {
            CredentialHolder.deleteWalletInfo(context);
            EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_HOME));
          }
        }
      }, CredentialHolder.getAddress(), transactionIn);
    } catch (Exception e) {
      EventBus.getDefault().post(new OnStatusError(BaseActivity.REQUEST_CODE_HOME));
    }
  }
}
