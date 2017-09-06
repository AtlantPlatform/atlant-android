package com.frostchein.atlant.activities.export_wallet;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.utils.BitmapUtils;
import com.frostchein.atlant.utils.CredentialHolder;
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
