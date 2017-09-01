package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.FragmentTransactionsModule;
import com.frostchein.atlant.fragments.transactions.TransactionsFragment;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = FragmentTransactionsModule.class
)
public interface FragmentTransactionsComponent {

  void inject(TransactionsFragment activity);
}

