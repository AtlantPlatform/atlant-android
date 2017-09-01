package com.frostchein.atlant.activities.settings;

import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.utils.CredentialHolder;
import javax.inject.Inject;

public class SettingsPresenterImpl implements SettingsPresenter, BasePresenter {

  private SettingsView view;

  @Inject
  public SettingsPresenterImpl(SettingsView view) {
    this.view = view;
  }


  @Override
  public void onDeleteWallet() {
    CredentialHolder.deleteWallet(view.getContext());
    view.restartApp();
  }
}
