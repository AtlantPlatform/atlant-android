package com.frostchein.atlant.activities.main;

import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.utils.CredentialHolder;
import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter, BasePresenter {

  private MainView view;

  @Inject
  MainPresenterImpl(MainView view) {
    this.view = view;
  }

  @Override
  public void onCreate() {
    if (view != null) {
      if (CredentialHolder.isFileExist(view.getContext(), CredentialHolder.PATH_WALLET)) {
        if (CredentialHolder.isLogged()) {
          view.onStartHome();
        } else {
          view.onStartAuthorisation();
        }
      } else {
        //view.onStartSelected();
        view.onStartSelectedApp();
      }
    }
  }

}
