/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.fragments.transactions;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import atlant.wallet.R;
import io.atlant.wallet.adapters.TransactionsAdapter;
import io.atlant.wallet.dagger2.component.AppComponent;
import io.atlant.wallet.dagger2.component.DaggerFragmentTransactionsComponent;
import io.atlant.wallet.dagger2.component.FragmentTransactionsComponent;
import io.atlant.wallet.dagger2.modules.FragmentTransactionsModule;
import io.atlant.wallet.fragments.base.BaseFragment;
import io.atlant.wallet.utils.VerticalSpaceItemDecoration;
import java.util.ArrayList;
import javax.inject.Inject;

public class TransactionsFragment extends BaseFragment implements TransactionsFragmentView {

  @BindView(R.id.transactions_recycler)
  RecyclerView recyclerView;

  @Inject
  TransactionsFragmentPresenter presenter;

  public static TransactionsFragment newInstance() {
    return new TransactionsFragment();
  }

  @Override
  protected void onCreated(View view, Bundle savedInstanceState) {
    recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(4));
  }

  public void update(ArrayList<Object> arrayList) {
    TransactionsAdapter adapter = new TransactionsAdapter(arrayList);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_transactions;
  }

  @Override
  protected void setupComponent(AppComponent appComponent) {
    FragmentTransactionsComponent component = DaggerFragmentTransactionsComponent.builder()
        .appComponent(appComponent)
        .fragmentTransactionsModule(new FragmentTransactionsModule(this))
        .build();
    component.inject(this);
  }
}
