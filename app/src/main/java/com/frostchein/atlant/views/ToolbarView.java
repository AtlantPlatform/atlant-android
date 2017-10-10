package com.frostchein.atlant.views;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.R;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DigitsUtils;
import com.frostchein.atlant.utils.tokens.Token;
import com.squareup.picasso.Picasso;
import java.math.BigInteger;

public class ToolbarView extends BaseCustomView {

  @BindView(R.id.toolbar_tabs)
  TabLayout tabLayout;
  @BindView(R.id.toolbar_horizontal_scroll)
  HorizontalScrollView horizontalScrollView;
  @BindView(R.id.toolbar_title)
  TextView textTitle;
  @BindView(R.id.toolbar_value)
  TextView textValue;
  @BindView(R.id.toolbar_chart)
  ImageView imChart;

  private CallBack callback;

  public interface CallBack {

    void onItemsClick(int pos);

  }

  public void setCallback(CallBack callback) {
    this.callback = callback;
  }

  public ToolbarView(Context context) {
    super(context);
  }

  public ToolbarView(Context context, AttributeSet attrs) {
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
    try {
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

        String s = DigitsUtils.valueToString(new BigInteger(value));
        textValue.setText(s);

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void deleteTitle() {
    if (textTitle != null) {
      ((ViewGroup) textTitle.getParent()).removeView(textTitle);
    }
  }

  public void deleteChart() {
    if (imChart != null) {
      ((ViewGroup) imChart.getParent()).removeView(imChart);
    }
  }

  public void updateChart(boolean isTransactionsShow) {
    if (isTransactionsShow) {
      Picasso.with(getContext()).load(R.drawable.home_chart_1).into(imChart);
    } else {
      Picasso.with(getContext()).load(R.drawable.home_chart_0).into(imChart);
    }
  }
}
