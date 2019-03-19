/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.import_wallet;

import io.atlant.wallet.activities.base.BasePresenter;
import javax.inject.Inject;
import org.web3j.crypto.WalletUtils;

public class ImportPresenterImpl implements ImportPresenter, BasePresenter {

  private ImportView view;

  @Inject
  ImportPresenterImpl(ImportView view) {
    this.view = view;
  }


  @Override
  public void onCreate() {
    onValidatePrivateKey();
  }

  @Override
  public void onValidatePrivateKey() {
    if (view != null) {
      view.stateBtContinue(WalletUtils.isValidPrivateKey(view.getPrivateKey()));
    }
  }
}
