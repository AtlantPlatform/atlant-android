package com.frostchein.atlant.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.frostchein.atlant.R;
import com.frostchein.atlant.model.TransactionItems;
import com.frostchein.atlant.views.TransactionItemView;
import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.MyViewHolder> {

  private ArrayList<TransactionItems> arrayItems;

  class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.liner)
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

  public TransactionsAdapter(ArrayList<TransactionItems> arrayItems) {
    this.arrayItems = arrayItems;
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView;
    if (viewType == 1) {
      itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_transaction, parent, false);
    } else {
      itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_transaction_emptu, parent, false);
    }
    return new MyViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(MyViewHolder holder, int position) {
    if (position != getItemCount() - 1) {
      TransactionItems transactionItems = arrayItems.get(position);
      holder.transactionItemView.setContent(transactionItems);

      if (position % 2 != 0) {
        holder.linearLayout.setBackgroundResource(R.color.transactions_background);
      } else {
        holder.linearLayout.setBackgroundResource(R.color.alpha);
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
