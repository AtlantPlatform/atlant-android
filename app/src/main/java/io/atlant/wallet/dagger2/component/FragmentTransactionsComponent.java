/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.dagger2.component;

import io.atlant.wallet.dagger2.ActivityScope;
import io.atlant.wallet.dagger2.modules.FragmentTransactionsModule;
import io.atlant.wallet.fragments.transactions.TransactionsFragment;
import dagger.Component;
import io.atlant.wallet.fragments.transactions.TransactionsFragment;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = FragmentTransactionsModule.class
)
public interface FragmentTransactionsComponent {

  void inject(TransactionsFragment activity);
}

