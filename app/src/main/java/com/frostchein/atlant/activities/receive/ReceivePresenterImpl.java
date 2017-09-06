package com.frostchein.atlant.activities.receive;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.utils.BitmapUtils;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.IntentUtils.EXTRA_STRING;
import com.frostchein.atlant.utils.ParserDataFromQr;
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
    onGenerationQR(0);
  }

  @Override
  public void onGenerationQR(double value) {
    line = ParserDataFromQr.generationLine(CredentialHolder.getAddress(), value);
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
