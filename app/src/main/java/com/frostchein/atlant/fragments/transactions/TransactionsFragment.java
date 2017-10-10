package com.frostchein.atlant.fragments.transactions;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.frostchein.atlant.R;
import com.frostchein.atlant.adapters.TransactionsAdapter;
import com.frostchein.atlant.dagger2.component.AppComponent;
import com.frostchein.atlant.dagger2.component.DaggerFragmentTransactionsComponent;
import com.frostchein.atlant.dagger2.component.FragmentTransactionsComponent;
import com.frostchein.atlant.dagger2.modules.FragmentTransactionsModule;
import com.frostchein.atlant.fragments.base.BaseFragment;
import com.frostchein.atlant.utils.VerticalSpaceItemDecoration;
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
