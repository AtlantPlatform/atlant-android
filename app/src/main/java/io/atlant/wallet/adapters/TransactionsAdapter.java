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

package io.atlant.wallet.adapters;

import android.support.v7.widget.RecyclerView;
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
