/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.views;


import android.content.Context;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import io.atlant.wallet.Config;
import atlant.wallet.R;
import io.atlant.wallet.model.TransactionsItem;
import io.atlant.wallet.model.TransactionsTokensItem;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.DateUtils;
import io.atlant.wallet.utils.DigitsUtils;
import java.math.BigInteger;

public class TransactionItemView extends BaseCustomView {

  @BindView(R.id.linear)
  LinearLayout linearLayout;
  @BindView(R.id.view_transaction_image)
  ImageView imStatus;
  @BindView(R.id.view_transaction_date_text)
  TextView dateTextView;
  @BindView(R.id.view_transaction_status_text)
  TextView statusTextView;
  @BindView(R.id.view_transaction_value_text)
  TextView countTextView;

  public TransactionItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TransactionItemView(Context context) {
    super(context);
  }

  @Override
  protected void initView() {

  }

  @Override
  protected int getLayoutRes() {
    return R.layout.view_transaction;
  }

  /**
   * Sets content on view
   *
   * @param objects Transaction object.
   */
  @Override
  public void setContent(Object... objects) {
      if (objects != null) {

        BigInteger bigIntegerData = null;
        BigInteger bigIntegerValue = null;

        if (objects[0] instanceof TransactionsItem) {
          TransactionsItem transactionsItem = (TransactionsItem) objects[0];

          bigIntegerData = DigitsUtils.getBase10FromString(transactionsItem.getTimeStamp());
          bigIntegerValue = DigitsUtils.getBase10FromString(transactionsItem.getValue());

          if (!transactionsItem.getFrom().equalsIgnoreCase(CredentialHolder.getAddress())) {
            setReceived();
          } else {
            setSent();
          }

          if (transactionsItem.getFrom().equalsIgnoreCase(transactionsItem.getTo())) {
            setSelf();
          }
        }

        if (objects[0] instanceof TransactionsTokensItem) {
          TransactionsTokensItem transactionsTokensItem = (TransactionsTokensItem) objects[0];

          bigIntegerData = DigitsUtils.getBase10from16(transactionsTokensItem.getTimeStamp());
          bigIntegerValue = DigitsUtils.getBase10from16(transactionsTokensItem.getData());

          if (transactionsTokensItem.isTransactionsIn()) {
            setReceived();
          } else {
            setSent();
          }
        }

        if (bigIntegerData != null && bigIntegerValue != null) {
          setDate(bigIntegerData);
          setValue(bigIntegerValue);
        }
      }
  }

  private void setReceived() {
    //  statusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.transactions_received));
    linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transactions_received_bg));
    imStatus.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_transactions_received));
    statusTextView.setText(getResources().getString(R.string.transaction_status_received));
  }

  private void setSent() {
    // statusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.transactions_send));
    linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transactions_send_bg));
    imStatus.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_transactions_send));
    statusTextView.setText(getResources().getString(R.string.transaction_status_send));
  }

  private void setSelf() {
    linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transactions_self_bg));
    imStatus.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_transactions_self));
    statusTextView.setText(getResources().getString(R.string.transaction_status_self));
  }


  private void setDate(BigInteger date) {
    dateTextView.setText(DateUtils.getFormattedFullDate(date.longValue()));
  }

  private void setValue(BigInteger value) {
    String name;
    if (CredentialHolder.getCurrentToken() != null) {
      name = CredentialHolder.getCurrentToken().getName();
    } else {
      name = Config.WALLET_ETH;
    }
    countTextView.setText(DigitsUtils.valueToString(value) + " " + name);
  }

}
