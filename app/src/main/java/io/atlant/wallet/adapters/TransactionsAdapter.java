/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import atlant.wallet.R;
import io.atlant.wallet.views.TransactionItemView;
import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.MyViewHolder> {

  private ArrayList<Object> arrayItems;

  class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.linear)
    LinearLayout linearLayout;
    @BindView(R.id.transaction_item_view)
    TransactionItemView transactionItemView;

    MyViewHolder(View view) {
      super(view);
      try {
        ButterKnife.bind(this, view);
      } catch (Exception ignored) {

      }
    }
  }

  public TransactionsAdapter(ArrayList<Object> arrayItems) {
    this.arrayItems = arrayItems;
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView;
    if (viewType == 1) {
      itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_transaction, parent, false);
    } else {
      itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_transaction_emptu, parent, false);
    }
    return new MyViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(MyViewHolder holder, int position) {
    if (position != getItemCount() - 1) {
      try {
        holder.transactionItemView.setContent(arrayItems.get(position));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public int getItemCount() {
    return arrayItems.size() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    if (position < getItemCount() - 1) {
      return 1;
    } else {
      return 0;
    }
  }
}
