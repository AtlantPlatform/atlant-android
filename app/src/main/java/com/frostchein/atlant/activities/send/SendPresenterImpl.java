package com.frostchein.atlant.activities.send;

import android.os.Handler;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.events.network.OnStatusError;
import com.frostchein.atlant.events.network.OnStatusSuccess;
import com.frostchein.atlant.events.network.OnStatusTimeOut;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.rest.AtlantApi;
import com.frostchein.atlant.rest.AtlantClient;
import com.frostchein.atlant.rest.NetModule;
import com.frostchein.atlant.rest.TransactionRestHandler;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DigitsUtils;
import com.frostchein.atlant.utils.WalletLoading;
import com.frostchein.atlant.utils.tokens.Token;
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

  @Inject
  SendPresenterImpl(SendView view) {
    this.view = view;
    AtlantApi atlantApi = NetModule.getRetrofit(Config.ENDPOINT_URL).create(AtlantApi.class);
    atlantClient = new AtlantClient(atlantApi);
    walletLoading = new WalletLoading(atlantClient, view, BaseActivity.REQUEST_CODE_SEND);
    walletLoading.setCallBack(this);
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

      if (value > Double.parseDouble(DigitsUtils.valueToString(new BigInteger(balance.getResult())))) {
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
      TransactionRestHandler.preparationTransaction(atlantClient, CredentialHolder.getAddress());
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
                TransactionRestHandler.sendTransactionToken(
                    atlantClient,
                    onStatusSuccess.getNonce(), onStatusSuccess.getGasPrice(),
                    view.getAddress(), view.getValue(),
                    CredentialHolder.getCredentials(), Config.GAS_LIMIT,
                    token.getContractAddress(),
                    token.getContractId());
              } else {
                TransactionRestHandler.sendTransaction(atlantClient,
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
