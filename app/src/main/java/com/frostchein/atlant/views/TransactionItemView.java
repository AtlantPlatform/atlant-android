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
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.R;
import com.frostchein.atlant.model.TransactionsItem;
import com.frostchein.atlant.model.TransactionsTokensItem;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DateUtils;
import com.frostchein.atlant.utils.DigitsUtils;
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
