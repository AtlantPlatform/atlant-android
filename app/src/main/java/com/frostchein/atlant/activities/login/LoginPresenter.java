package com.frostchein.atlant.activities.login;

import com.frostchein.atlant.activities.base.BasePresenter;

public interface LoginPresenter extends BasePresenter {

  void onCreate(int typeResult, String privateKey);

  void dropLogin();

  void onPasswordEntered();
}
