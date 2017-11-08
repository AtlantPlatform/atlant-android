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
import java.util.Calendar;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomePresenterImpl implements HomePresenter, WalletLoading.OnCallBack, BasePresenter {

  private HomeView view;
  private WalletLoading walletLoading;

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

  private int[] getPointChart(ArrayList<Object> arrayListTransactions, int currentDay) {
    BigInteger time = null;
    BigInteger value = null;

    //init
    Calendar cal = Calendar.getInstance();
    BigInteger[] bigIntegers = new BigInteger[7];
    for (int i = 0; i < bigIntegers.length; i++) {
      bigIntegers[i] = BigInteger.ZERO;
    }

    for (int i = 0; i < arrayListTransactions.size(); i++) {
      Object object = arrayListTransactions.get(i);

      //get time & value
      if (object instanceof TransactionsItem) {
        time = DigitsUtils.getBase10FromString(((TransactionsItem) object).getTimeStamp());
        value = DigitsUtils.getBase10FromString(((TransactionsItem) object).getValue());
      }

      if (object instanceof TransactionsTokensItem) {
        time = DigitsUtils.getBase10from16(((TransactionsTokensItem) object).getTimeStamp());
        value = DigitsUtils.getBase10from16(((TransactionsTokensItem) object).getData());
      }

      //day since the beginning of the week
      cal.setTimeInMillis(time.longValue() * 1000);
      int numberDay = cal.get(Calendar.DAY_OF_WEEK);

      //current week
      if (numberDay > currentDay) {
        break;
      }

      //sum of all values
      bigIntegers[numberDay - 1] = bigIntegers[numberDay - 1].add(value);
    }

    //find max value
    BigInteger maxBigInteger = new BigInteger(String.valueOf(bigIntegers[0]));
    for (int i = 0; i < bigIntegers.length; i++) {
      if (bigIntegers[i].compareTo(maxBigInteger) == 1) {
        maxBigInteger = bigIntegers[i];
      }
    }

    //range from 0 to 100
    int[] point = new int[currentDay];
    for (int i = 0; i < currentDay; i++) {
      if (maxBigInteger.intValue() != 0) {
        bigIntegers[i] = bigIntegers[i].multiply(BigInteger.valueOf(100)).divide(maxBigInteger);
      }
      point[i] = bigIntegers[i].intValue();
    }
    return point;
  }

  private int[] getPointChartNoTransactions(int currentDay) {
    int[] point = new int[currentDay];
    for (int i = 0; i < point.length; i++) {
      point[i] = 0;
    }
    return point;
  }

  private int getCurrentDay() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(System.currentTimeMillis());
    return cal.get(Calendar.DAY_OF_WEEK);
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
    int currentDay = getCurrentDay();
    if (transactions != null) {
      int[] pointChart;
      if (transactions instanceof Transactions
          && ((Transactions) transactions).getTransactionsItem() != null
          && ((Transactions) transactions).getTransactionsItem().size() > 0) {

        ArrayList<Object> list = convertArrayListToObject((transactions));
        pointChart = getPointChart(list, currentDay);
        view.setTransactionsOnFragment(list, pointChart);

      } else if (transactions instanceof TransactionsTokens
          && ((TransactionsTokens) transactions).getTransactionsTokensItems() != null
          && ((TransactionsTokens) transactions).getTransactionsTokensItems().size() > 0) {

        ArrayList<Object> list = convertArrayListToObject((transactions));
        pointChart = getPointChart(list, currentDay);
        view.setTransactionsOnFragment(list, pointChart);

      } else {
        view.setNoTransactionsOnView(getPointChartNoTransactions(currentDay));
      }
    } else {
      view.setNoTransactionsOnView(getPointChartNoTransactions(currentDay));
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
