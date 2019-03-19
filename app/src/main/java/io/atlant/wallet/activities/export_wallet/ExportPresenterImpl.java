/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.export_wallet;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import io.atlant.wallet.Config;
import io.atlant.wallet.activities.base.BasePresenter;
import io.atlant.wallet.utils.BitmapUtils;
import io.atlant.wallet.utils.CredentialHolder;
import javax.inject.Inject;

public class ExportPresenterImpl implements ExportPresenter, BasePresenter {

  private ExportView view;

  @Inject
  ExportPresenterImpl(ExportView view) {
    this.view = view;
  }

  @Override
  public void onCreate() {
    view.setPrivateKey(CredentialHolder.getPrivateKey());
    new GenerationQR().execute();
  }

  private class GenerationQR extends AsyncTask<Void, Void, Void> {

    private Bitmap bitmap;

    @Override
    protected Void doInBackground(Void... params) {
      try {
        bitmap = BitmapUtils.QR.generateBitmap(CredentialHolder.getPrivateKey(), Config.SIZE_PX_QR, Config.SIZE_PX_QR,
            BitmapUtils.QR.MARGIN_NONE);
      } catch (Exception ignored) {

      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      if (bitmap != null && view != null) {
        view.setQR(bitmap);
      }
    }
  }
}
