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
import com.frostchein.atlant.model.TransactionsItem;
import com.frostchein.atlant.model.TransactionsTokens;
import com.frostchein.atlant.model.TransactionsTokensItem;
import com.frostchein.atlant.rest.AtlantApi;
import com.frostchein.atlant.rest.AtlantClient;
import com.frostchein.atlant.rest.NetModule;
import com.frostchein.atlant.utils.DigitsUtils;
import com.frostchein.atlant.utils.WalletLoading;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomePresenterImpl implements HomePresenter, WalletLoading.OnCallBack, BasePresenter {

  private HomeView view;
  private WalletLoading walletLoading;
  private int[] pointChartNoTransactions = {0, 0, 0, 0};
  private int[] pointChart = {0, 0, 5, 3, 120};

  @Inject
  HomePresenterImpl(HomeView view) {
    this.view = view;
    AtlantApi atlantApi = NetModule.getRetrofit(Config.ENDPOINT_URL).create(AtlantApi.class);
    AtlantClient atlantClient = new AtlantClient(atlantApi);
    walletLoading = new WalletLoading(atlantClient, view, BaseActivity.REQUEST_CODE_HOME);
    walletLoading.setCallBack(this);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    if (view != null) {
      Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          walletLoading.onUpdateLocal();
          view.onRefreshStart();
          walletLoading.refreshContent();
        }
      }, 10);
    }
  }

  @Override
  public void onChangeValue(int pos) {
    walletLoading.onChangeValue(pos);
  }

  @Override
  public void onUpdateLocal() {
    walletLoading.onUpdateLocal();
  }

  @Override
  public void refreshContent() {
    walletLoading.refreshContent();
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

  private int[] getPointChart(ArrayList<Object> arrayList) {
    BigInteger time = null;
    BigInteger value = null;

    long currentTime = System.currentTimeMillis() / 1000L;

    for (int i = 0; i < arrayList.size(); i++) {
      Object object = arrayList.get(i);

      if (object instanceof TransactionsItem) {
        time = DigitsUtils.getBase10FromString(((TransactionsItem) object).getTimeStamp());
        value = DigitsUtils.getBase10FromString(((TransactionsItem) object).getValue());
      }

      if (object instanceof TransactionsTokensItem) {
        time = DigitsUtils.getBase10from16(((TransactionsTokensItem) object).getTimeStamp());
        value = DigitsUtils.getBase10from16(((TransactionsTokensItem) object).getData());
      }

      long l=currentTime-time.longValue();
      System.out.println(l);
      System.out.println(l/3600/12);

    }

    return pointChart;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onSuccess(OnStatusSuccess onStatusSuccess) {
    if (onStatusSuccess.getRequest() != BaseActivity.REQUEST_CODE_HOME) {
      return;
    }
    walletLoading.onSuccess(onStatusSuccess);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onError(OnStatusError onStatusError) {
    if (onStatusError.getRequest() != BaseActivity.REQUEST_CODE_HOME) {
      return;
    }
    walletLoading.onError(onStatusError);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onTimeOut(OnStatusTimeOut onStatusTimeOut) {
    if (onStatusTimeOut.getRequest() != BaseActivity.REQUEST_CODE_HOME) {
      return;
    }
    walletLoading.onTimeOut(onStatusTimeOut);
  }

  @Override
  public void responseBalance(Balance balance) {
    view.setContentOnToolbar(balance);
  }

  @Override
  public void responseTransactions(Object transactions) {
    if (transactions != null) {
      if (transactions instanceof Transactions
          && ((Transactions) transactions).getTransactionsItem() != null
          && ((Transactions) transactions).getTransactionsItem().size() > 0) {

        ArrayList<Object> list = convertArrayListToObject((transactions));
        pointChart = getPointChart(list);
        view.setTransactionsOnFragment(list, pointChart);

      } else if (transactions instanceof TransactionsTokens
          && ((TransactionsTokens) transactions).getTransactionsTokensItems() != null
          && ((TransactionsTokens) transactions).getTransactionsTokensItems().size() > 0) {

        ArrayList<Object> list = convertArrayListToObject((transactions));
        pointChart = getPointChart(list);
        view.setTransactionsOnFragment(list, pointChart);

      } else {
        view.setNoTransactionsOnView(pointChartNoTransactions);
      }
    } else {
      view.setNoTransactionsOnView(pointChartNoTransactions);
    }
  }

  @Override
  public void onLoadingError() {
    view.onLoadingError();
  }

  @Override
  public void onTimeOut() {
    view.onTimeout();
  }
}
