package com.frostchein.atlant.views;

import android.content.Context;
import android.util.AttributeSet;
import com.frostchein.atlant.R;

public class NoTransactionView extends BaseCustomView {

  public NoTransactionView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NoTransactionView(Context context) {
    super(context);
  }

  @Override
  protected void initView() {

  }

  @Override
  protected int getLayoutRes() {
    return R.layout.view_no_transactions;
  }

  @Override
  public void setContent(Object... objects) {

  }
}
