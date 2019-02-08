/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
 */

package io.atlant.wallet.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
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
