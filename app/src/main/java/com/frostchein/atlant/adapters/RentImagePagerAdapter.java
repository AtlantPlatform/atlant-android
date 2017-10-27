package com.frostchein.atlant.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.frostchein.atlant.R;
import com.frostchein.atlant.utils.PicassoTargetUtils;
import com.frostchein.atlant.utils.PicassoTargetUtils.CallBack;
import com.frostchein.atlant.utils.ScreenUtils;
import com.frostchein.atlant.views.ImageViewRound;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class RentImagePagerAdapter extends PagerAdapter {

  @BindView(R.id.progress_bar)
  MaterialProgressBar progressBar;
  @BindView(R.id.rent_detail_gallery_image)
  ImageViewRound imageView;

  private ArrayList<String> arrayListURL;
  private LayoutInflater inflater;
  private Context context;

  public RentImagePagerAdapter(Context context, ArrayList<String> arrayListURL) {
    this.context = context;
    this.arrayListURL = arrayListURL;
    inflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return arrayListURL.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view.equals(object);
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public Object instantiateItem(final ViewGroup container, final int position) {
    final View view = inflater.inflate(R.layout.adapter_rent_detail_gallery, container, false);
    ButterKnife.bind(this, view);
    final PicassoTargetUtils picassoTargetUtils = new PicassoTargetUtils(progressBar, imageView);
    picassoTargetUtils.setCallBack(new CallBack() {
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
            Picasso.with(context).load(arrayListURL.get(position)).resize(ScreenUtils.getWidth(context), 0)
                .error(R.drawable.rent_warning).into(picassoTargetUtils.getTarget());

          }
        }, 10);
      }
    });

    Picasso.with(context).load(arrayListURL.get(position)).resize(ScreenUtils.getWidth(context), 0)
        .error(R.drawable.rent_warning).into(picassoTargetUtils.getTarget());
    container.addView(view);
    return view;
  }
}
