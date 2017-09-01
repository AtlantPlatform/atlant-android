package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.receive.ReceivePresenter;
import com.frostchein.atlant.activities.receive.ReceivePresenterImpl;
import com.frostchein.atlant.activities.receive.ReceiveView;
import dagger.Module;
import dagger.Provides;

@Module
public class ReceiveActivityModule {

  private ReceiveView view;

  public ReceiveActivityModule(ReceiveView view) {
    this.view = view;
  }

  @Provides
  ReceiveView provideView() {
    return view;
  }

  @Provides
  ReceivePresenter providePresenter(ReceivePresenterImpl presenter) {
    return presenter;
  }

}
