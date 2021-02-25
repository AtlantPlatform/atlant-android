/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.receive;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import io.atlant.wallet.Config;
import io.atlant.wallet.activities.base.BasePresenter;
import io.atlant.wallet.utils.BitmapUtils;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.IntentUtils.EXTRA_STRING;
import javax.inject.Inject;

public class ReceivePresenterImpl implements ReceivePresenter, BasePresenter {

  private ReceiveView view;
  private String line;

  @Inject
  ReceivePresenterImpl(ReceiveView view) {
    this.view = view;
  }

  @Override
  public void onCopyAddress() {
    if (view != null) {
      ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
      ClipData clip = ClipData.newPlainText(EXTRA_STRING.ADDRESS, CredentialHolder.getAddress());
      clipboard.setPrimaryClip(clip);
      view.onKeyCopied();
    }
  }

  @Override
  public void onCreated() {
    view.setAddress(CredentialHolder.getAddress());
    line = CredentialHolder.getAddress();
    new GenerationQR().execute();
  }

  private class GenerationQR extends AsyncTask<Void, Void, Void> {

    private Bitmap bitmap;

    @Override
    protected Void doInBackground(Void... params) {
      try {
        bitmap = BitmapUtils.QR.generateBitmap(line, Config.SIZE_PX_QR, Config.SIZE_PX_QR, BitmapUtils.QR.MARGIN_NONE);
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
