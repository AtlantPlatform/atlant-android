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

  public void onChangeValue(int pos) {
    CredentialHolder.setNumberToken(view.getContext(), pos);
    onUpdateLocal();
    view.onRefreshStart();
    refreshContent();
  }

  public void onUpdateLocal() {
    new AsyncTaskLoadLocal().execute(view);
  }

  private class AsyncTaskLoadLocal extends AsyncTask<BaseView, Void, Void> {

    Object transactions;
    Balance balance;

    @Override
    protected Void doInBackground(BaseView... baseView) {
      if (CredentialHolder.getCurrentToken() == null) {
        transactions = CredentialHolder.getTransaction(baseView[0].getContext());
        balance = CredentialHolder.getBalance(baseView[0].getContext());
      } else {
        transactions = CredentialHolder.getTransaction(baseView[0].getContext(), CredentialHolder.getCurrentToken());
        balance = CredentialHolder.getBalance(baseView[0].getContext(), CredentialHolder.getCurrentToken());
      }
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
      final Balance balance = onStatusSuccess.getBalance();
      final Object transactions = onStatusSuccess.getTransactions();
      final Token token = CredentialHolder.getCurrentToken();

      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          CredentialHolder.saveWalletInfo(view.getContext(), balance, transactions, token);
        }
      });
      thread.start();

      if (callBack != null) {
        callBack.responseBalance(balance);
        callBack.responseTransactions(transactions);
      }
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
