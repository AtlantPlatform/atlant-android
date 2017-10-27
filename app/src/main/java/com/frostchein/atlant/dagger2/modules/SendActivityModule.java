package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.send.SendPresenter;
import com.frostchein.atlant.activities.send.SendPresenterImpl;
import com.frostchein.atlant.activities.send.SendView;
import com.frostchein.atlant.views.ToolbarWalletView;
import dagger.Module;
import dagger.Provides;

@Module
public class SendActivityModule {

  private SendView view;

  public SendActivityModule(SendView view) {
    this.view = view;
  }

  @Provides
  SendView provideView() {
    return view;
  }

  @Provides
  SendPresenter providePresenter(SendPresenterImpl presenter) {
    return presenter;
  }

  @Provides
  ToolbarWalletView provideToolbar() {
    return new ToolbarWalletView(view.getContext());
  }

}
