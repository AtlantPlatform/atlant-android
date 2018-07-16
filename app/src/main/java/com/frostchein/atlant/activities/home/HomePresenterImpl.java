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

package com.frostchein.atlant.activities.home;

import android.content.SharedPreferences;
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
import com.frostchein.atlant.rest.WalletLoading;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DigitsUtils;
import com.frostchein.atlant.utils.SharedPreferencesUtils;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomePresenterImpl implements HomePresenter, WalletLoading.OnCallBack, BasePresenter {

  private HomeView view;
  private WalletLoading walletLoading;
  private Balance balance;

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

      SharedPreferences preferences = CredentialHolder.loadSetting(view.getContext());
      if (preferences.getBoolean(SharedPreferencesUtils.TAG_NEW_PRIVATE_KEY, false)) {
        view.onStartActivityBackup();
      }
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

  private int[] getPointChart(Balance balance, ArrayList<Object> arrayListTransactions) {
    BigInteger time = null;
    BigInteger value = null;
    BigInteger currentBalance = DigitsUtils.getBase10FromString(balance.getResult());

    //init
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(System.currentTimeMillis());

    Calendar calCurrentDay = Calendar.getInstance();
    calCurrentDay.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

    long timeCurrentDay = calCurrentDay.getTimeInMillis() / 1000;
    long timeTransactions;

    BigInteger[] bigIntegers = new BigInteger[7];
    for (int i = 0; i < bigIntegers.length; i++) {
      bigIntegers[i] = BigInteger.ZERO;
    }

    for (int i = 0; i < arrayListTransactions.size(); i++) {
      Object object = arrayListTransactions.get(i);

      if (object instanceof TransactionsItem) {
        time = DigitsUtils.getBase10FromString(((TransactionsItem) object).getTimeStamp());
        value = DigitsUtils.getBase10FromString(((TransactionsItem) object).getValue());

        BigInteger gas = DigitsUtils.getBase10FromString(((TransactionsItem) object).getGas());
        BigInteger price = DigitsUtils.getBase10FromString(((TransactionsItem) object).getGasPrice());
        BigInteger commission = gas.multiply(price);

        if (((TransactionsItem) object).getFrom().equalsIgnoreCase(CredentialHolder.getAddress())) {
          if (((TransactionsItem) object).getFrom().equalsIgnoreCase(((TransactionsItem) object).getTo())) {
            value = BigInteger.ZERO;
          }
          value = value.add(commission);
        } else {
          value = value.multiply(new BigInteger("-1"));
        }
      }

      if (object instanceof TransactionsTokensItem) {
        time = DigitsUtils.getBase10from16(((TransactionsTokensItem) object).getTimeStamp());
        value = DigitsUtils.getBase10from16(((TransactionsTokensItem) object).getData());

        if (((TransactionsTokensItem) object).isTransactionsIn()) {
          value = value.multiply(new BigInteger("-1"));
        }
      }
      timeTransactions = time.longValue();

      if (timeCurrentDay - timeTransactions >= 0) {
        long days = TimeUnit.SECONDS.toDays(timeCurrentDay - timeTransactions) + 1;

        //only weeks (first 7 days)
        if (days >= 6) {
          break;
        }
        bigIntegers[(int) days] = bigIntegers[(int) days].add(value);
      } else {
        bigIntegers[0] = bigIntegers[0].add(value);
      }
    }

    //add balance
    BigInteger[] arrayBalanceWeek = new BigInteger[7];
    arrayBalanceWeek[6] = currentBalance;
    for (int i = 0; i < bigIntegers.length; i++) {
      if (6 - i - 1 > -1) {
        arrayBalanceWeek[6 - i - 1] = arrayBalanceWeek[6 - i].add(bigIntegers[i]);
      }
    }

    //find max value
    BigInteger maxBigInteger = new BigInteger(String.valueOf(bigIntegers[0]));
    for (BigInteger anArrayBalanceWeek : arrayBalanceWeek) {
      if (anArrayBalanceWeek.compareTo(maxBigInteger) == 1) {
        maxBigInteger = anArrayBalanceWeek;
      }
    }

    //range from 0 to 100
    int[] point = new int[bigIntegers.length];
    for (int i = 0; i < bigIntegers.length; i++) {
      if (maxBigInteger.intValue() != 0) {
        arrayBalanceWeek[i] = arrayBalanceWeek[i].multiply(BigInteger.valueOf(100)).divide(maxBigInteger);
      }
      point[i] = arrayBalanceWeek[i].intValue();
    }
    return point;
  }

  private int[] getPointChartNoTransactions() {
    int[] point = new int[7];
    for (int i = 0; i < point.length; i++) {
      point[i] = 0;
    }
    return point;
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
    this.balance = balance;
    view.setContentOnToolbar(balance);
  }

  @Override
  public void responseTransactions(Object transactions) {
    if (transactions != null) {
      int[] pointChart;
      if (transactions instanceof Transactions
          && ((Transactions) transactions).getTransactionsItem() != null
          && ((Transactions) transactions).getTransactionsItem().size() > 0) {

        ArrayList<Object> list = convertArrayListToObject((transactions));
        try {
          pointChart = getPointChart(balance, list);
        } catch (Exception e) {
          e.printStackTrace();
          view.onLoadingError();
          pointChart = getPointChartNoTransactions();
        }
        view.setTransactionsOnFragment(list, pointChart);

      } else if (transactions instanceof TransactionsTokens
          && ((TransactionsTokens) transactions).getTransactionsTokensItems() != null
          && ((TransactionsTokens) transactions).getTransactionsTokensItems().size() > 0) {

        ArrayList<Object> list = convertArrayListToObject((transactions));
        try {
          pointChart = getPointChart(balance, list);
        } catch (Exception e) {
          e.printStackTrace();
          view.onLoadingError();
          pointChart = getPointChartNoTransactions();
        }
        view.setTransactionsOnFragment(list, pointChart);

      } else {
        view.setNoTransactionsOnView(getPointChartNoTransactions());
      }
    } else {
      view.setNoTransactionsOnView(getPointChartNoTransactions());
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
