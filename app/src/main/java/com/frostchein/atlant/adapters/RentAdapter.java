package com.frostchein.atlant.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.frostchein.atlant.R;
import com.frostchein.atlant.model.rent.Rent;
import com.frostchein.atlant.utils.PicassoTargetUtils;
import com.frostchein.atlant.utils.ScreenUtils;
import com.frostchein.atlant.views.ImageViewRound;
import com.frostchein.atlant.views.LikeView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.MyViewHolder> {

  private ArrayList<Rent> arrayItems;
  private CallBack callBack;

  public interface CallBack {

    void onSelected(int pos);
  }

  public void setCallBack(CallBack callBack) {

    this.callBack = callBack;
  }

  class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.linear)
    LinearLayout linearLayout;
    @BindView(R.id.progress_bar)
    MaterialProgressBar materialProgressBar;
    @BindView(R.id.rent_item_image)
    ImageViewRound imageView;
    @BindView(R.id.rent_item_name_text)
    TextView textName;
    @BindView(R.id.rent_item_address_text)
    TextView textCity;
    @BindView(R.id.rent_item_rooms_text)
    TextView textRooms;
    @BindView(R.id.rent_item_beds_text)
    TextView textBeds;
    @BindView(R.id.rent_item_price_text)
    TextView textPrice;
    @BindView(R.id.rent_item_how_day_text)
    TextView textHowDay;
    @BindView(R.id.rent_item_down_up)
    ImageView imTriangle;
    @BindView(R.id.rent_item_like)
    LikeView likeView;

    PicassoTargetUtils target;

    MyViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      target = new PicassoTargetUtils(materialProgressBar, imageView);
    }
  }

  public RentAdapter(ArrayList<Rent> arrayItems) {
    this.arrayItems = arrayItems;
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_rent, parent, false);
    return new MyViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, final int position) {

    Rent rent = arrayItems.get(position);
    Context context = holder.linearLayout.getContext();

    holder.linearLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (callBack != null) {
          callBack.onSelected(position);
        }
      }
    });

    Picasso.with(context).load(rent.getImageUrl()[0]).resize(ScreenUtils.getWidth(context), 0)
        .error(R.drawable.rent_warning).into(holder.target.getTarget());

    holder.textName.setText(rent.getName());
    holder.textCity.setText(rent.getCountry() + ", " + rent.getCity());
    String rooms = context.getString(R.string.rent_main_rooms);
    holder.textRooms.setText(String.valueOf(rent.getNumberRooms()) + " " + rooms);
    String beds = context.getString(R.string.rent_main_beds);
    holder.textBeds.setText(String.valueOf(rent.getNumberBeds()) + " " + beds);
    holder.textPrice.setText("$ " + String.valueOf(rent.getPriceDollars()));
    holder.textHowDay.setText(String.valueOf(rent.getHowDay()));
    holder.likeView.setLike(rent.getNumberLike());

    if (rent.isGrowth()) {
      holder.imTriangle.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_transactions_received));
    } else {
      holder.imTriangle.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_transactions_send));
    }
  }

  @Override
  public int getItemCount() {
    return arrayItems.size();
  }
}
