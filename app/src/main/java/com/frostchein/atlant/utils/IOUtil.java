package com.frostchein.atlant.utils;

import android.text.TextUtils;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

final class IOUtil {

  private IOUtil() {
  }

  static void saveFileWithText(File file, String text) {
    if (file != null && !TextUtils.isEmpty(text)) {
      FileOutputStream fos;
      try {
        fos = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);
        bufferedOutputStream.write(text.getBytes());
        bufferedOutputStream.close();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  static String getTextFromFile(File file) {
    if (file != null) {
      FileInputStream fis;
      try {
        fis = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
        String line = bufferedReader.readLine();
        bufferedReader.close();
        fis.close();
        return line;
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    } else {
      return null;
    }
  }

  static boolean isFileExist(File file) {
    return file != null && file.exists();
  }

  static boolean deleteFile(File file) {
    return file != null && file.delete();
  }
}
