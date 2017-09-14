package com.frostchein.atlant.activities.home;

import android.os.Bundle;
import android.os.Handler;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.activities.base.BaseActivity;
import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.events.network.OnStatusError;
import com.frostchein.atlant.events.network.OnStatusSuccess;
import com.frostchein.atlant.events.network.OnStatusTimeOut;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.Transactions;
import com.frostchein.atlant.model.TransactionsTokens;
import com.frostchein.atlant.rest.AtlantApi;
import com.frostchein.atlant.rest.AtlantClient;
import com.frostchein.atlant.rest.NetModule;
import com.frostchein.atlant.rest.WalletRestHandler;
import com.frostchein.atlant.utils.ConnectivityUtils;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.tokens.Token;
import java.util.ArrayList;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomePresenterImpl implements HomePresenter, BasePresenter {

  private HomeView view;
  private AtlantClient atlantClient;

  @Inject
  HomePresenterImpl(HomeView view) {
    this.view = view;
    AtlantApi atlantApi = NetModule.getRetrofit(Config.ENDPOINT_URL).create(AtlantApi.class);
    atlantClient = new AtlantClient(atlantApi);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    if (view != null) {
      Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          onUpdateLocal();
          view.onRefreshStart();
          refreshContent();
        }
      }, 10);
    }
  }

  @Override
  public void onChangeValue(int pos) {
    CredentialHolder.setNumberToken(view.getContext(), pos);
    onUpdateLocal();
    view.onRefreshStart();
    refreshContent();
  }

  @Override
  public void onUpdateLocal() {
    Object transactions;
    Balance balance;
    if (CredentialHolder.getCurrentToken() == null) {
      transactions = CredentialHolder.getTransaction(view.getContext());
      balance = CredentialHolder.getBalance(view.getContext());
    } else {
      transactions = CredentialHolder.getTransaction(view.getContext(), CredentialHolder.getCurrentToken());
      balance = CredentialHolder.getBalance(view.getContext(), CredentialHolder.getCurrentToken());
    }
    setViewWalletInfo(balance, transactions);
  }

  @Override
  public void refreshContent() {
    if (view != null) {
      if (ConnectivityUtils.isNetworkOnline(view.getContext())) {
        try {

          String address = CredentialHolder.getAddress();

          WalletRestHandler.cancel();
          if (CredentialHolder.getCurrentToken() == null) {
            WalletRestHandler.requestWalletInfo(atlantClient, address);
          } else {
            Token token = CredentialHolder.getCurrentToken();
            WalletRestHandler.requestWalletInfo(atlantClient, address, token);
          }


        } catch (Exception e) {
          e.printStackTrace();
          view.onLoadingError();
          view.onRefreshComplete();
        }
      } else {
        view.onNoInternetConnection();
        view.onRefreshComplete();
      }
    }
  }

  private void setViewWalletInfo(Balance balance, Object transactions) {
    if (view != null) {
      view.setContentOnToolbar(balance);
      if (transactions != null) {

        if (transactions instanceof Transactions
            && ((Transactions) transactions).getTransactionsItem() != null
            && ((Transactions) transactions).getTransactionsItem().size() > 0) {
          view.setTransactionsOnFragment(convertArrayListToObject((transactions)));
        } else if (transactions instanceof TransactionsTokens
            && ((TransactionsTokens) transactions).getTransactionsTokensItems() != null
            && ((TransactionsTokens) transactions).getTransactionsTokensItems().size() > 0) {
          view.setTransactionsOnFragment(convertArrayListToObject((transactions)));
        } else {
          view.setNoTransactionsOnView();
        }

      } else {
        view.setNoTransactionsOnView();
      }
      view.onRefreshComplete();
    }
  }

  private ArrayList<Object> convertArrayListToObject(Object object) {
    ArrayList<Object> arrayList = new ArrayList<>();

    if (object instanceof Transactions) {
      for (int i = 0; i < ((Transactions) object).getTransactionsItem().size(); i++) {
        arrayList.add(((Transactions) object).getTransactionsItem().get(i));
      }
    }

    if (object instanceof TransactionsTokens) {
      for (int i = 0; i < ((TransactionsTokens) object).getTransactionsTokensItems().size(); i++) {
        arrayList.add(((TransactionsTokens) object).getTransactionsTokensItems().get(i));
      }
    }

    return arrayList;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onSuccess(OnStatusSuccess onStatusSuccess) {
    if (onStatusSuccess.getRequest() != BaseActivity.REQUEST_CODE_HOME) {
      return;
    }
    if (view != null) {
      Balance balance = onStatusSuccess.getBalance();
      Object transactions = onStatusSuccess.getTransactions();
      Token token = CredentialHolder.getCurrentToken();

      CredentialHolder.saveWalletInfo(view.getContext(), balance, transactions, token);
      setViewWalletInfo(balance, transactions);
      view.onRefreshComplete();
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onError(OnStatusError onStatusError) {
    if (onStatusError.getRequest() != BaseActivity.REQUEST_CODE_HOME) {
      return;
    }
    if (view != null) {
      view.onLoadingError();
      view.onRefreshComplete();
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onTimeOut(OnStatusTimeOut onStatusTimeOut) {
    if (onStatusTimeOut.getRequest() != BaseActivity.REQUEST_CODE_HOME) {
      return;
    }
    if (view != null) {
      view.onTimeout();
      view.onRefreshComplete();
    }
  }
}
