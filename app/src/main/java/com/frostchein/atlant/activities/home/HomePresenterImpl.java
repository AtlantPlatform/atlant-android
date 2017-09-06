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
import com.frostchein.atlant.rest.AtlantApi;
import com.frostchein.atlant.rest.AtlantClient;
import com.frostchein.atlant.rest.NetModule;
import com.frostchein.atlant.rest.WalletRestHandler;
import com.frostchein.atlant.utils.ConnectivityUtils;
import com.frostchein.atlant.utils.CredentialHolder;
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
  public void onUpdateLocal() {
    setViewWalletInfo(CredentialHolder.getWalletBalance(view.getContext()),
        CredentialHolder.getWalletTransaction(view.getContext()));
  }

  @Override
  public void refreshContent() {
    if (view != null) {
      if (ConnectivityUtils.isNetworkOnline(view.getContext())) {
        WalletRestHandler.requestWalletBalance(view.getContext(), atlantClient);
      } else {
        view.onNoInternetConnection();
        view.onRefreshComplete();
      }
    }
  }

  private void setViewWalletInfo(Balance balance, Transactions transactions) {
    if (view != null) {
      view.setContentOnToolbar(balance);
      if (transactions != null && transactions.getTransactionItems() != null
          && transactions.getTransactionItems().size() > 0) {
        view.setTransactionsRecyclerFragmentOnView(transactions.getTransactionItems());
      } else {
        view.setNoTransactionsOnView();
      }
      view.onRefreshComplete();
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onSuccess(OnStatusSuccess onStatusSuccess) {
    if (onStatusSuccess.getRequest() != BaseActivity.REQUEST_CODE_HOME) {
      return;
    }
    if (view != null) {
      Balance balance = onStatusSuccess.getBalance();
      Transactions transactions = onStatusSuccess.getTransactions();
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
