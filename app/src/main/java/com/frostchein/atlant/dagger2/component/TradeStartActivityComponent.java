package com.frostchein.atlant.dagger2.component;

import com.frostchein.atlant.activities.trade_start.TradeStartActivity;
import com.frostchein.atlant.dagger2.ActivityScope;
import com.frostchein.atlant.dagger2.modules.TradeStartActivityModule;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = TradeStartActivityModule.class
)
public interface TradeStartActivityComponent {

  void inject(TradeStartActivity activity);
}

