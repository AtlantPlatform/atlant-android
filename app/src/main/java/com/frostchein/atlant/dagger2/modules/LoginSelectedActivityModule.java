package com.frostchein.atlant.dagger2.modules;

import com.frostchein.atlant.activities.login_selected.LoginSelectedPresenter;
import com.frostchein.atlant.activities.login_selected.LoginSelectedPresenterImpl;
import com.frostchein.atlant.activities.login_selected.LoginSelectedView;
import dagger.Module;
import dagger.Provides;

@Module
public class LoginSelectedActivityModule {

  private LoginSelectedView view;

  public LoginSelectedActivityModule(LoginSelectedView view) {
    this.view = view;
  }

  @Provides
  LoginSelectedView provideView() {
    return view;
  }

  @Provides
  LoginSelectedPresenter providePresenter(LoginSelectedPresenterImpl presenter) {
    return presenter;
  }

}
