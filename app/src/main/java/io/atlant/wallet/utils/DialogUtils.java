/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import atlant.wallet.R;
import io.atlant.wallet.activities.base.BaseActivity;
import io.atlant.wallet.views.DialogError;

public final class DialogUtils {

  private static Dialog dialog;

  private static OnTouchListener onTouchListener = new OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
      if (BaseActivity.timerLogOut) {
        BaseActivity.resetDisconnectTimer();
      }
      return false;
    }
  };

  public static void openDialogChoice(final Context context,
      final int titleRes, final String message,
      final int positiveRes, final int negativeRes,
      final DialogInterface.OnClickListener dialogListener) {
    hideDialog();
    new Handler().post(new Runnable() {
      @Override
      public void run() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
            new ContextThemeWrapper(context, R.style.DialogStyle));
        dialog = builder.setTitle(titleRes)
            .setMessage(message)
            .setOnDismissListener(new DialogInterface.OnDismissListener() {
              @Override
              public void onDismiss(DialogInterface dialog) {
                DialogUtils.dialog = null;
              }
            })
            .setPositiveButton(positiveRes, dialogListener)
            .setNegativeButton(negativeRes, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
              }
            })
            .setCancelable(true)
            .show();
        dialog.getWindow().getDecorView().setOnTouchListener(onTouchListener);
      }
    });
  }

  public static void openDialogProgress(final Context context, final String title, final String message) {
    hideDialog();
    new Handler().post(new Runnable() {
      @Override
      public void run() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        TextView textMessage = view.findViewById(R.id.dialog_message);
        textMessage.setText(message);
        AlertDialog.Builder builder = new AlertDialog.Builder(
            new ContextThemeWrapper(context, R.style.DialogStyle));
        dialog = builder.setTitle(title)
            .setView(view)
            .setOnDismissListener(new DialogInterface.OnDismissListener() {
              @Override
              public void onDismiss(DialogInterface dialog) {
                DialogUtils.dialog = null;
              }
            })
            .setCancelable(false)
            .show();
        dialog.getWindow().getDecorView().setOnTouchListener(onTouchListener);
      }
    });
  }

  public static void openDialogError(final Context context, final String title, final String message,
      @DrawableRes final int iconRes,
      final View.OnClickListener listener) {
    new Handler().post(new Runnable() {
      @Override
      public void run() {
        dialog = new DialogError(context, title, message, iconRes, listener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
          @Override
          public void onDismiss(DialogInterface dialog) {
            DialogUtils.dialog = null;
          }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        showDialogFullSize(context, 0.8f);
      }
    });
  }

  public static void hideDialog() {
    try {
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
      }
    } catch (Exception e) {
      dialog = null;
      e.printStackTrace();
    }
  }

  private static void showDialogFullSize(Context context, float scale) {
    Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    int width = size.x;
    int height = size.y;
    try {
      dialog.show();
      dialog.getWindow().getDecorView().setOnTouchListener(onTouchListener);
      WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
      Window window = dialog.getWindow();
      assert window != null;
      lp.copyFrom(window.getAttributes());
      lp.width = (int) (width * scale);
      lp.height = (int) (height * scale);
      window.setAttributes(lp);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
