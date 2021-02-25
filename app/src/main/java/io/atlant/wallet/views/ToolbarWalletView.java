/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.views;

import android.content.Context;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import butterknife.BindView;
import io.atlant.wallet.Config;
import atlant.wallet.R;
import io.atlant.wallet.model.Balance;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.DigitsUtils;
import io.atlant.wallet.utils.DimensUtils;
import io.atlant.wallet.utils.tokens.Token;
import java.math.BigInteger;

public class ToolbarWalletView extends BaseCustomView {

  @BindView(R.id.toolbar_tabs)
  TabLayout tabLayout;
  @BindView(R.id.toolbar_horizontal_scroll)
  HorizontalScrollView horizontalScrollView;
  @BindView(R.id.toolbar_title)
  TextView textTitle;
  @BindView(R.id.toolbar_value)
  TextView textValue;
  @BindView(R.id.toolbar_chart)
  ChartView chartView;


  private CallBack callback;

  public interface CallBack {

    void onItemsClick(int pos);

  }

  public void setCallback(CallBack callback) {
    this.callback = callback;
  }

  public ToolbarWalletView(Context context) {
    super(context);
  }

  public ToolbarWalletView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initView() {

    Token[] tokens = CredentialHolder.getTokens();
    String[] item = new String[tokens.length + 1];
    item[0] = Config.WALLET_ETH;

    for (int i = 0; i < tokens.length; i++) {
      item[1 + i] = tokens[i].getName();
    }

    for (String anItem : item) {
      tabLayout.addTab(tabLayout.newTab().setText(anItem));
    }

    selectTab();
    tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
      @Override
      public void onTabSelected(Tab tab) {
        if (callback != null) {
          callback.onItemsClick(tab.getPosition() - 1);
        }
      }

      @Override
      public void onTabUnselected(Tab tab) {

      }

      @Override
      public void onTabReselected(Tab tab) {

      }

    });
  }

  private void selectTab() {
    TabLayout.Tab tab = tabLayout.getTabAt(CredentialHolder.getNumberToken() + 1);
    assert tab != null;
    tab.select();
  }

  @Override
  protected int getLayoutRes() {
    return R.layout.view_toolbar;
  }

  /**
   * Sets content on view
   *
   * @param objects List of wallet entities.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void setContent(Object... objects) {

    if (objects != null && objects.length > 0) {
      Balance balance = (Balance) objects[0];

      String value = null;
      if (balance != null) {
        value = balance.getResult();
      }

      if (value == null || value.isEmpty()) {
        value = "0";
      }

      selectTab();

      String strBalance;
      try {
        strBalance = DigitsUtils.valueToString(new BigInteger(value));
        textValue.setText(strBalance);
      } catch (Exception e) {
        strBalance = "0";
        e.printStackTrace();

      }
      textValue.setText(strBalance);
    }
  }

  public void deleteTitle() {
    if (textTitle != null) {
      ((ViewGroup) textTitle.getParent()).removeView(textTitle);
    }
  }

  public void deleteChart() {
    if (chartView != null) {
      ((ViewGroup) chartView.getParent()).removeView(chartView);
    }
  }

  public void updateChart(int[] points) {
    ViewGroup.LayoutParams layoutParams = chartView.getLayoutParams();
    chartView.setPointChart(points, true);
    layoutParams.height = DimensUtils.dpToPx(getContext(), 80);
    for (int point : points) {
      if (points[0] != point) {
        layoutParams.height = DimensUtils.dpToPx(getContext(), 150);
      }
    }
    chartView.setLayoutParams(layoutParams);
  }
}
