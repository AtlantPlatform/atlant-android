package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.fragments.transactions.TransactionsFragmentPresenter;
import com.frostchein.atlant.fragments.transactions.TransactionsFragmentPresenterImpl;
import com.frostchein.atlant.fragments.transactions.TransactionsFragmentView;
import dagger.Module;
import dagger.Provides;

@Module
public class FragmentTransactionsModule {

  private TransactionsFragmentView view;

  public FragmentTransactionsModule(TransactionsFragmentView view) {
    this.view = view;
  }

  @Provides
  TransactionsFragmentView provideView() {
    return view;
  }

  @Provides
  TransactionsFragmentPresenter providePresenter(TransactionsFragmentPresenterImpl presenter) {
    return presenter;
  }

}
