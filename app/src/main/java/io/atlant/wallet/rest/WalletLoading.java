/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.rest;

import android.os.AsyncTask;
import io.atlant.wallet.activities.base.BaseView;
import io.atlant.wallet.events.network.OnStatusError;
import io.atlant.wallet.events.network.OnStatusSuccess;
import io.atlant.wallet.events.network.OnStatusTimeOut;
import io.atlant.wallet.model.Balance;
import io.atlant.wallet.utils.ConnectivityUtils;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.tokens.Token;

import io.atlant.wallet.activities.base.BaseView;
import io.atlant.wallet.events.network.OnStatusError;
import io.atlant.wallet.events.network.OnStatusSuccess;
import io.atlant.wallet.events.network.OnStatusTimeOut;
import io.atlant.wallet.model.Balance;
import io.atlant.wallet.utils.ConnectivityUtils;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.tokens.Token;

public class WalletLoading {

  private boolean isUpdateLocal = false;

  public interface OnCallBack {

    void responseBalance(Balance balance);

    void responseTransactions(Object Transactions);

    void onLoadingError();

    void onTimeOut();
  }

  private WalletRestHandler walletRestHandler;
  private AtlantClient atlantClient;
  private OnCallBack callBack;
  private int requestCode;
  private BaseView view;

  public void setCallBack(WalletLoading.OnCallBack callBack) {
    this.callBack = callBack;
  }

  public WalletLoading(AtlantClient atlantClient, BaseView view, int requestCode) {
    this.atlantClient = atlantClient;
    this.view = view;
    this.requestCode = requestCode;
    this.walletRestHandler = new WalletRestHandler();
  }

  public void refreshContent() {
    if (view != null) {
      if (ConnectivityUtils.isNetworkOnline(view.getContext())) {
        try {

          String address = CredentialHolder.getAddress();

          walletRestHandler.cancel();
          if (CredentialHolder.getCurrentToken() == null) {
            walletRestHandler.requestWalletInfo(atlantClient, address, requestCode);
            walletRestHandler.requestTransactions(atlantClient,address,requestCode);
          } else {
            Token token = CredentialHolder.getCurrentToken();
            walletRestHandler.requestWalletInfo(atlantClient, address, token, requestCode);
            walletRestHandler.requestTokenTransactions(atlantClient, address, token,true,null,requestCode);
          }

        } catch (Exception e) {
          e.printStackTrace();
          if (callBack != null) {
            callBack.onLoadingError();
          }
          view.onRefreshComplete();
        }
      } else {
        view.onNoInternetConnection();
        view.onRefreshComplete();
      }
    }
  }

  public void onChangeValue(final int pos) {
    if (!isUpdateLocal) {
      new AsyncTaskWait(pos).execute();
    }
  }

  public void onUpdateLocal() {
    new AsyncTaskLoadLocal().execute(view);
  }

  private class AsyncTaskWait extends AsyncTask<Void, Void, Void> {

    private int pos;

    AsyncTaskWait(int pos) {
      this.pos = pos;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      while (true) {
        if (!isUpdateLocal) {
          break;
        }
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      view.onRefreshStart();
      CredentialHolder.setNumberToken(view.getContext(), pos);
      onUpdateLocal();
      refreshContent();
    }
  }

  private class AsyncTaskLoadLocal extends AsyncTask<BaseView, Void, Boolean> {

    private Object transactions;
    private Balance balance;

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      isUpdateLocal = true;
    }

    @Override
    protected Boolean doInBackground(BaseView... baseView) {
      try {
        if (CredentialHolder.getCurrentToken() == null) {
          transactions = CredentialHolder.getTransaction(baseView[0].getContext());
          balance = CredentialHolder.getBalance(baseView[0].getContext());
        } else {
          transactions = CredentialHolder.getTransaction(baseView[0].getContext(), CredentialHolder.getCurrentToken());
          balance = CredentialHolder.getBalance(baseView[0].getContext(), CredentialHolder.getCurrentToken());
        }
        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
      return false;
    }

    @Override
    protected void onPostExecute(Boolean bol) {
      super.onPostExecute(bol);
      isUpdateLocal = false;
      if (bol) {
        if (callBack != null) {
          callBack.responseBalance(balance);
          callBack.responseTransactions(transactions);
        }
      }
    }
  }

  private class AsyncTaskSaveLocal extends AsyncTask<Void, Void, Void> {

    private Balance balance;
    private Object transactions;
    private Token token;

    AsyncTaskSaveLocal(Balance balance, Object transactions, Token token) {
      this.balance = balance;
      this.transactions = transactions;
      this.token = token;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      CredentialHolder.saveWalletInfo(view.getContext(), balance, transactions, token);
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      if (callBack != null) {
        callBack.responseBalance(balance);
        callBack.responseTransactions(transactions);
      }
    }
  }

  public void onSuccess(OnStatusSuccess onStatusSuccess) {
    if (view != null) {
      new AsyncTaskSaveLocal(onStatusSuccess.getBalance(), onStatusSuccess.getTransactions(),
          onStatusSuccess.getToken()).execute();
      view.onRefreshComplete();
    }
  }

  public void onError(OnStatusError onStatusError) {
    if (view != null) {
      if (callBack != null) {
        callBack.onLoadingError();
      }
      view.onRefreshComplete();
    }
  }

  public void onTimeOut(OnStatusTimeOut onStatusTimeOut) {
    if (view != null) {
      if (callBack != null) {
        callBack.onTimeOut();
      }
      view.onRefreshComplete();
    }
  }
}
