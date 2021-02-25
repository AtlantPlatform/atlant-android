/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.main;

import io.atlant.wallet.activities.base.BasePresenter;
import io.atlant.wallet.utils.CredentialHolder;
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
