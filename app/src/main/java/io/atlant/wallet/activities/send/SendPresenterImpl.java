/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.send;

import android.os.Handler;
import io.atlant.wallet.Config;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.activities.base.BasePresenter;
import io.atlant.wallet.events.network.OnStatusError;
import io.atlant.wallet.events.network.OnStatusSuccess;
import io.atlant.wallet.events.network.OnStatusTimeOut;
import io.atlant.wallet.model.Balance;
import io.atlant.wallet.rest.AtlantApi;
import io.atlant.wallet.rest.AtlantClient;
import io.atlant.wallet.rest.NetModule;
import io.atlant.wallet.rest.TransactionRestHandler;
import io.atlant.wallet.rest.WalletLoading;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.DigitsUtils;
import io.atlant.wallet.utils.tokens.Token;
import java.math.BigInteger;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.web3j.crypto.WalletUtils;

public class SendPresenterImpl implements SendPresenter, WalletLoading.OnCallBack, BasePresenter {

  private SendView view;
  private AtlantClient atlantClient;
  private WalletLoading walletLoading;
  private boolean isPrepare = true;
  private boolean isUpdate = false;
  private Balance balance;

  private TransactionRestHandler transactionRestHandler;

  @Inject
  SendPresenterImpl(SendView view) {
    this.view = view;
    AtlantApi atlantApi = NetModule.getRetrofit(Config.ENDPOINT_URL).create(AtlantApi.class);
    atlantClient = new AtlantClient(atlantApi);
    walletLoading = new WalletLoading(atlantClient, view, BaseActivity.REQUEST_CODE_SEND);
    walletLoading.setCallBack(this);
    transactionRestHandler = new TransactionRestHandler();
  }

  @Override
  public void onCreate(String line) {
    if (view != null) {
      init();
      if (WalletUtils.isValidAddress(line)) {
        view.setAddress(line);
      }
      view.setBalance(balance);
    }
  }

  private void init() {
    Token token = CredentialHolder.getCurrentToken();
    if (token == null) {
      balance = CredentialHolder.getBalance(view.getContext());
      view.setWalletName(Config.WALLET_ETH);
      view.setContentOnToolbar(balance);
    } else {
      balance = CredentialHolder.getBalance(view.getContext(), token);
      view.setWalletName(token.getName());
      view.setContentOnToolbar(balance);
    }
  }

  @Override
  public void onChangeValue(int pos) {
    isUpdate = true;
    walletLoading.onChangeValue(pos);
  }


  @Override
  public void refreshContent() {
    isUpdate = true;
    walletLoading.refreshContent();
  }


  @Override
  public void onValidate() {
    if (view != null) {

      String address = view.getAddress();
      if (!WalletUtils.isValidAddress(address)) {
        view.onInvalidAddress();
        return;
      }

      double value;
      try {
        value = Double.parseDouble(view.getValue());
        if (value < 0.000000000000000001) {
          view.onFormatMoney();
          return;
        }
      } catch (Exception e) {
        view.onFormatMoney();
        return;
      }

      try {
        if (value > Double.parseDouble(DigitsUtils.valueToString(new BigInteger(balance.getResult())))) {
          view.onNoMoney();
          return;
        }
      } catch (NumberFormatException e) {
        e.printStackTrace();
        view.onNoMoney();
        return;
      }

      view.dialogConfirmTransaction();
    }
  }

  @Override
  public void onSendTransaction() {
    if (view != null) {
      isPrepare = true;
      isUpdate = false;
      view.showProgressDialog(view.getContext().getString(R.string.send_preparing));
      transactionRestHandler.preparationTransaction(atlantClient, CredentialHolder.getAddress());
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onError(final OnStatusError onStatusError) {
    if (view != null) {
      if (onStatusError.getRequest() != BaseActivity.REQUEST_CODE_SEND) {
        return;
      }
      if (isUpdate) {
        walletLoading.onError(onStatusError);
        return;
      }
      view.hideProgressDialog();

      Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          if (onStatusError.getMessage() == null) {
            view.onError(view.getContext().getString(R.string.send_error_send));
          } else {
            view.onError(onStatusError.getMessage());
          }
        }
      }, 500);

    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onTimeout(OnStatusTimeOut onStatusTimeOut) {
    if (view != null) {
      if (onStatusTimeOut.getRequest() != BaseActivity.REQUEST_CODE_SEND) {
        return;
      }
      if (isUpdate) {
        walletLoading.onTimeOut(onStatusTimeOut);
        return;
      }
      view.hideProgressDialog();
      view.onTimeout();
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onSuccess(final OnStatusSuccess onStatusSuccess) {
    if (view != null) {
      if (onStatusSuccess.getRequest() != BaseActivity.REQUEST_CODE_SEND) {
        return;
      }
      if (isUpdate) {
        walletLoading.onSuccess(onStatusSuccess);
        init();
        return;
      }
      view.hideProgressDialog();

      if (isPrepare) {
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            isPrepare = false;

            try {
              Token token = CredentialHolder.getCurrentToken();
              if (token != null) {
                transactionRestHandler.sendTransactionToken(
                    atlantClient,
                    onStatusSuccess.getNonce(), onStatusSuccess.getGasPrice(),
                    view.getAddress(), view.getValue(),
                    CredentialHolder.getCredentials(), Config.GAS_LIMIT,
                    token.getContractAddress(),
                    token.getContractId());
              } else {
                transactionRestHandler.sendTransaction(atlantClient,
                    onStatusSuccess.getNonce(), onStatusSuccess.getGasPrice(),
                    view.getAddress(), view.getValue(),
                    CredentialHolder.getCredentials(), Config.GAS_LIMIT);
              }
              view.showProgressDialog(view.getContext().getString(R.string.send_process));
            } catch (Exception e) {
              e.printStackTrace();
              view.hideProgressDialog();

              Handler handler1 = new Handler();
              handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                  view.onError(view.getContext().getString(R.string.send_error_send));
                }
              }, 500);
            }

          }
        }, 100);
      } else {
        view.onSuccessfulSend();
      }
    }
  }

  @Override
  public void responseBalance(Balance balance) {
    view.setContentOnToolbar(balance);
  }

  @Override
  public void responseTransactions(Object Transactions) {

  }

  @Override
  public void onLoadingError() {
    view.onError(view.getContext().getString(R.string.system_wallet_loading_error));
  }

  @Override
  public void onTimeOut() {
    view.onTimeout();
  }
}
