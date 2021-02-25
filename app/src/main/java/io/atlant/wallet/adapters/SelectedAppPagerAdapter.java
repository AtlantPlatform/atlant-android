/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import atlant.wallet.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import io.atlant.wallet.model.SelectedApp;
import io.atlant.wallet.utils.PicassoTargetUtils;
import io.atlant.wallet.utils.ScreenUtils;
import io.atlant.wallet.views.ImageViewRound;
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
            Picasso.with(context).load(arrayList.get(position).getResDrawable()).resize(ScreenUtils.getWidth(context), 0)
                .error(R.drawable.warning).into(picassoTargetUtils.getTarget());
          }
        }, 10);
      }
    });

    Picasso.with(context).load(arrayList.get(position).getResDrawable())
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
