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

package com.frostchein.atlant.views;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import butterknife.BindView;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.R;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DigitsUtils;
import com.frostchein.atlant.utils.DimensUtils;
import com.frostchein.atlant.utils.tokens.Token;
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
