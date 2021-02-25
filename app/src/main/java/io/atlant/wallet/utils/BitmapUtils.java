/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.annotation.NonNull;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.EnumMap;
import java.util.Map;

public class BitmapUtils {

  /**
   * Generation QR
   */
  public static class QR {

    /**
     * Allow the zxing engine use the default argument for the margin variable
     */
    static int MARGIN_AUTOMATIC = -1;
    /**
     * Set no margin to be added to the QR code by the zxing engine
     */
    static public int MARGIN_NONE = 0;
    static private int colorWhite = Color.WHITE;
    static private int colorBack = Color.BLACK;

    static public Bitmap generateBitmap(@NonNull String contentsToEncode,
        int imageWidth, int imageHeight,
        int marginSize) throws WriterException {

      Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
      hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

      if (marginSize != MARGIN_AUTOMATIC) {
        // We want to generate with a custom margin size
        hints.put(EncodeHintType.MARGIN, marginSize);
      }

      MultiFormatWriter writer = new MultiFormatWriter();
      BitMatrix result = writer
          .encode(contentsToEncode, BarcodeFormat.QR_CODE, imageWidth, imageHeight, hints);

      final int width = result.getWidth();
      final int height = result.getHeight();
      int[] pixels = new int[width * height];
      for (int y = 0; y < height; y++) {
        int offset = y * width;
        for (int x = 0; x < width; x++) {
          pixels[offset + x] = result.get(x, y) ? colorBack : colorWhite;
        }
      }
      Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
      return bitmap;
    }
  }
}

