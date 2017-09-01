package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.login.LoginPresenter;
import com.frostchein.atlant.activities.login.LoginPresenterImpl;
import com.frostchein.atlant.activities.login.LoginView;
import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

  private LoginView view;

  public LoginActivityModule(LoginView view) {
    this.view = view;
  }

  @Provides
  LoginView provideView() {
    return view;
  }

  @Provides
  LoginPresenter providePresenter(LoginPresenterImpl presenter) {
    return presenter;
  }

}
