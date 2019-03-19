/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.utils;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView.ScaleType;
import io.atlant.wallet.views.ImageViewRound;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

import io.atlant.wallet.views.ImageViewRound;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class PicassoTargetUtils {

  private Target target;
  private MaterialProgressBar materialProgressBar;
  private ImageViewRound imageView;
  private CallBack callBack;

  public interface CallBack {

    void onBitmapLoaded();

    void onBitmapFailed();

    void onPrepareLoad();

  }

  public void setCallBack(CallBack callBack) {
    this.callBack = callBack;
  }

  public PicassoTargetUtils(MaterialProgressBar materialProgressBar, ImageViewRound imageView) {
    this.materialProgressBar = materialProgressBar;
    this.imageView = imageView;
    createTarget();
  }

  private void createTarget() {
    target = new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
        materialProgressBar.setVisibility(View.INVISIBLE);
        imageView.setScaleType(ScaleType.CENTER_CROP);
        imageView.setColor(Color.TRANSPARENT);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
        if (callBack != null) {
          callBack.onBitmapLoaded();
        }
      }

      @Override
      public void onBitmapFailed(Drawable errorDrawable) {
        materialProgressBar.setVisibility(View.INVISIBLE);
        imageView.setScaleType(ScaleType.CENTER_INSIDE);
        imageView.setImageDrawable(errorDrawable);
        if (callBack != null) {
          callBack.onBitmapFailed();
        }
      }

      @Override
      public void onPrepareLoad(Drawable placeHolderDrawable) {
        if (callBack != null) {
          callBack.onPrepareLoad();
        }
      }
    };
  }

  public Target getTarget() {
    return target;
  }
}

