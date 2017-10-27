package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.login_selected_app.LoginSelectedAppPresenter;
import com.frostchein.atlant.activities.login_selected_app.LoginSelectedAppPresenterImpl;
import com.frostchein.atlant.activities.login_selected_app.LoginSelectedAppView;
import dagger.Module;
import dagger.Provides;

@Module
public class LoginSelectedAppActivityModule {

  private LoginSelectedAppView view;

  public LoginSelectedAppActivityModule(LoginSelectedAppView view) {
    this.view = view;
  }

  @Provides
  LoginSelectedAppView provideView() {
    return view;
  }

  @Provides
  LoginSelectedAppPresenter providePresenter(LoginSelectedAppPresenterImpl presenter) {
    return presenter;
  }

}
