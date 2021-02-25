/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.modules;

import io.atlant.wallet.fragments.transactions.TransactionsFragmentPresenter;
import io.atlant.wallet.fragments.transactions.TransactionsFragmentPresenterImpl;
import io.atlant.wallet.fragments.transactions.TransactionsFragmentView;
import dagger.Module;
import dagger.Provides;
import io.atlant.wallet.fragments.transactions.TransactionsFragmentPresenter;
import io.atlant.wallet.fragments.transactions.TransactionsFragmentPresenterImpl;
import io.atlant.wallet.fragments.transactions.TransactionsFragmentView;

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
