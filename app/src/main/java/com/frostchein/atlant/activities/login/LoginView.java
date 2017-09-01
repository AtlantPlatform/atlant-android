package com.frostchein.atlant.activities.login;

import com.frostchein.atlant.activities.base.BaseView;

public interface LoginView extends BaseView {

  void setLoginText(String text);

  void requestPasswordState(boolean viewState);

  void onWrongPassword();

  void onSuccessfulAuthorisation(String password);

  void onSuccessfulChangePassword(String password);

  String getPassword();
}
