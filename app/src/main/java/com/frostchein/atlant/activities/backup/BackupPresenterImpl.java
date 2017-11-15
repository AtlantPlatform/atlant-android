package com.frostchein.atlant.activities.backup;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.utils.BitmapUtils;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.SharedPreferencesUtils;
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
