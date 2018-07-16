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

package com.frostchein.atlant.rest;

import android.os.AsyncTask;
import com.frostchein.atlant.activities.base.BaseView;
import com.frostchein.atlant.events.network.OnStatusError;
import com.frostchein.atlant.events.network.OnStatusSuccess;
import com.frostchein.atlant.events.network.OnStatusTimeOut;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.utils.ConnectivityUtils;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.tokens.Token;

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
