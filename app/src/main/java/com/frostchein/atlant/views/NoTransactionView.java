package com.frostchein.atlant.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import butterknife.BindView;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.R;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.tokens.Token;

public class NoTransactionView extends BaseCustomView {

  @BindView(R.id.view_no_transactions_message)
  TextView textMessage;

  public NoTransactionView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NoTransactionView(Context context) {
    super(context);
  }

  @Override
  protected void initView() {
    String name = Config.WALLET_ETH;
    Token token = CredentialHolder.getCurrentToken();
    if (token != null) {
      name = token.getName();
    }
    String text = String.format(getResources().getString(R.string.no_transactions_messages), name);
    textMessage.setText(text);
  }

  @Override
  protected int getLayoutRes() {
    return R.layout.view_no_transactions;
  }

  @Override
  public void setContent(Object... objects) {

  }

  @Override
  public void invalidate() {
    initView();
  }
}
