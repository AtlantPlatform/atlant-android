package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.trade_start.TradeStartPresenter;
import com.frostchein.atlant.activities.trade_start.TradeStartPresenterImpl;
import com.frostchein.atlant.activities.trade_start.TradeStartView;
import dagger.Module;
import dagger.Provides;

@Module
public class TradeStartActivityModule {

  private TradeStartView view;

  public TradeStartActivityModule(TradeStartView view) {
    this.view = view;
  }

  @Provides
  TradeStartView provideView() {
    return view;
  }

  @Provides
  TradeStartPresenter providePresenter(TradeStartPresenterImpl presenter) {
    return presenter;
  }

}
