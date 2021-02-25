/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.settings;

import io.atlant.wallet.activities.base.BasePresenter;
import io.atlant.wallet.utils.CredentialHolder;
import javax.inject.Inject;

public class SettingsPresenterImpl implements SettingsPresenter, BasePresenter {

  private SettingsView view;

  @Inject
  SettingsPresenterImpl(SettingsView view) {
    this.view = view;
  }


  @Override
  public void onDeleteWallet() {
    CredentialHolder.deleteWallet(view.getContext());
    view.restartApp();
  }
}
