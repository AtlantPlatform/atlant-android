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

package com.frostchein.atlant.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.frostchein.atlant.R;
import com.frostchein.atlant.model.SelectedApp;
import com.frostchein.atlant.utils.PicassoTargetUtils;
import com.frostchein.atlant.utils.ScreenUtils;
import com.frostchein.atlant.views.ImageViewRound;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class SelectedAppPagerAdapter extends BaseAdapterScrollCircular {

  @BindView(R.id.progress_bar)
  MaterialProgressBar progressBar;
  @BindView(R.id.login_selected_app_relative)
  RelativeLayout relativeLayout;
  @BindView(R.id.login_selected_app_image)
  ImageViewRound imageView;
  @BindView(R.id.login_selected_app_title)
  TextView textTitle1;
  @BindView(R.id.login_selected_app_title2)
  TextView textTitle2;

  private ArrayList<SelectedApp> arrayList;
  private LayoutInflater inflater;
  private Context context;

  private CallBack callBack;

  public interface CallBack {

    void onClickItems(int pos);

  }

  public void setCallBack(CallBack callBack) {
    this.callBack = callBack;
  }

  public SelectedAppPagerAdapter(Context context, ArrayList<SelectedApp> arrayList) {
    super(arrayList);
    this.arrayList = arrayList;
    this.context = context;
    inflater = LayoutInflater.from(context);
  }

  @Override
  public View createView(ViewGroup container, final int position) {
    View view = inflater.inflate(R.layout.adapter_selected_app, container, false);
    ButterKnife.bind(this, view);

    final PicassoTargetUtils picassoTargetUtils = new PicassoTargetUtils(progressBar, imageView);
    picassoTargetUtils.setCallBack(new PicassoTargetUtils.CallBack() {
      @Override
      public void onBitmapLoaded() {
      }

      @Override
      public void onBitmapFailed() {
      }

      @Override
      public void onPrepareLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            Picasso.with(context).load(arrayList.get(position).getUrl()).resize(ScreenUtils.getWidth(context), 0)
                .error(R.drawable.warning).into(picassoTargetUtils.getTarget());
          }
        }, 10);
      }
    });

    Picasso.with(context).load(arrayList.get(position).getUrl()).resize(ScreenUtils.getWidth(context), 0)
        .error(R.drawable.warning).into(picassoTargetUtils.getTarget());

    relativeLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (callBack != null) {
          callBack.onClickItems(position - 1);
        }
      }
    });

    textTitle1.setText(arrayList.get(position).getTitle1());
    textTitle2.setText(arrayList.get(position).getTitle2());

    float alpha = 0.3f;
    if (position != 1 && position != 2 && position != 4) {
      textTitle1.setAlpha(alpha);
      textTitle2.setAlpha(alpha);
    }

    container.addView(view);
    return view;
  }
}
