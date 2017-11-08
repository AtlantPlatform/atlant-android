package com.frostchein.atlant.utils;

import android.os.AsyncTask;
import com.frostchein.atlant.activities.base.BaseView;
import com.frostchein.atlant.events.network.OnStatusError;
import com.frostchein.atlant.events.network.OnStatusSuccess;
import com.frostchein.atlant.events.network.OnStatusTimeOut;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.rest.AtlantClient;
import com.frostchein.atlant.rest.WalletRestHandler;
import com.frostchein.atlant.utils.tokens.Token;

public class WalletLoading {

  private boolean isUpdateLocal = false;

  public interface OnCallBack {

    void responseBalance(Balance balance);

    void responseTransactions(Object Transactions);

    void onLoadingError();

    void onTimeOut();
  }


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
  }

  public void refreshContent() {
    if (view != null) {
      if (ConnectivityUtils.isNetworkOnline(view.getContext())) {
        try {

          String address = CredentialHolder.getAddress();

          WalletRestHandler.cancel();
          if (CredentialHolder.getCurrentToken() == null) {
            WalletRestHandler.requestWalletInfo(atlantClient, address, requestCode);
          } else {
            Token token = CredentialHolder.getCurrentToken();
            WalletRestHandler.requestWalletInfo(atlantClient, address, token, requestCode);
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
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          if (!isUpdateLocal) {
            break;
          }
        }
        CredentialHolder.setNumberToken(view.getContext(), pos);
        onUpdateLocal();
        refreshContent();
      }
    });
    thread.start();
    view.onRefreshStart();
  }

  public void onUpdateLocal() {
    new AsyncTaskLoadLocal().execute(view);
  }

  private class AsyncTaskLoadLocal extends AsyncTask<BaseView, Void, Void> {

    Object transactions;
    Balance balance;

    @Override
    protected Void doInBackground(BaseView... baseView) {
      isUpdateLocal = true;
      if (CredentialHolder.getCurrentToken() == null) {
        transactions = CredentialHolder.getTransaction(baseView[0].getContext());
        balance = CredentialHolder.getBalance(baseView[0].getContext());
      } else {
        transactions = CredentialHolder.getTransaction(baseView[0].getContext(), CredentialHolder.getCurrentToken());
        balance = CredentialHolder.getBalance(baseView[0].getContext(), CredentialHolder.getCurrentToken());
      }
      isUpdateLocal = false;
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

  private class AsyncTaskSaveLocal extends AsyncTask<BaseView, Void, Void> {

    private Balance balance;
    private Object transactions;
    private Token token;

    AsyncTaskSaveLocal(Balance balance, Object transactions, Token token) {
      this.balance = balance;
      this.transactions = transactions;
      this.token = token;
    }

    @Override
    protected Void doInBackground(BaseView... baseView) {
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
