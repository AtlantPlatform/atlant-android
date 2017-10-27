package com.frostchein.atlant.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.frostchein.atlant.R;
import com.frostchein.atlant.model.rent.RentCity;
import com.frostchein.atlant.utils.PicassoTargetUtils;
import com.frostchein.atlant.utils.ScreenUtils;
import com.frostchein.atlant.views.ImageViewRound;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class RentCityAdapter extends RecyclerView.Adapter<RentCityAdapter.MyViewHolder> {

  private ArrayList<RentCity> arrayItems;
  private CallBack callBack;

  public RentCityAdapter(ArrayList<RentCity> arrayItems) {
    this.arrayItems = arrayItems;
  }

  public void setCallBack(CallBack callBack) {
    this.callBack = callBack;
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_rent_city, parent, false);
    return new MyViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, final int position) {

    RentCity rent = arrayItems.get(position);
    Context context = holder.relativeLayout.getContext();

    Picasso.with(context).load(rent.getPathImage()).resize(ScreenUtils.getWidth(context), 0)
        .error(R.drawable.rent_warning).into(holder.target.getTarget());

    holder.textName.setText(rent.getName());

    holder.relativeLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (callBack != null) {
          callBack.onCityClick(position);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return arrayItems.size();
  }

  public interface CallBack {

    void onCityClick(int pos);

  }

  class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.relative)
    RelativeLayout relativeLayout;
    @BindView(R.id.progress_bar)
    MaterialProgressBar materialProgressBar;
    @BindView(R.id.rent_city_items_image)
    ImageViewRound imageView;
    @BindView(R.id.rent_city_items_name)
    TextView textName;

    PicassoTargetUtils target;

    MyViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      target = new PicassoTargetUtils(materialProgressBar, imageView);
    }
  }
}
