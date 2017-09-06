package com.frostchein.atlant.activities.import_wallet;

import com.frostchein.atlant.activities.base.BasePresenter;
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
