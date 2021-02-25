/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.backup;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import io.atlant.wallet.Config;
import io.atlant.wallet.utils.BitmapUtils;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.SharedPreferencesUtils;
import javax.inject.Inject;

public class BackupPresenterImpl implements BackupPresenter {

  private BackupView view;

  @Inject
  BackupPresenterImpl(BackupView view) {
    this.view = view;
  }

  @Override
  public void onCreate() {
    view.setPrivateKey(CredentialHolder.getPrivateKey());
    new GenerationQR().execute();
  }

  @Override
  public void onDone() {
    CredentialHolder.saveSetting(view.getContext(), SharedPreferencesUtils.TAG_NEW_PRIVATE_KEY, false);
    view.onFinish();
  }

  @Override
  public void checked(boolean bol) {
    view.stateBtContinue(bol);
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
