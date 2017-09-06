package com.frostchein.atlant.views;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;
import butterknife.BindView;
import com.frostchein.atlant.R;
import com.frostchein.atlant.model.TransactionItems;
import com.frostchein.atlant.utils.DateUtils;
import com.frostchein.atlant.utils.DigitsUtils;

public class TransactionItemView extends BaseCustomView {

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
      TransactionItems transactionItems = (TransactionItems) objects[0];

      dateTextView.setText(
          DateUtils.getFormattedFullDate(DigitsUtils.getBase10from16(transactionItems.getTimeStamp()).longValue()));

      countTextView.setText(
          DigitsUtils.ATLtoString(DigitsUtils.getBase10from16(transactionItems.getData())) + " "
              + getResources()
              .getString(R.string.app_prefix_coin));

      if (transactionItems.isTransactionsIn()) {
        statusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.transactions_received));
        statusTextView.setText(getResources().getString(R.string.transaction_status_received));
      } else {
        statusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.transactions_send));
        statusTextView.setText(getResources().getString(R.string.transaction_status_send));
      }
    }
  }
}
