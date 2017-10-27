package com.frostchein.atlant.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import butterknife.BindView;
import com.frostchein.atlant.R;
import com.frostchein.atlant.adapters.RentCityAdapter;
import com.frostchein.atlant.model.rent.RentCity;
import java.util.ArrayList;

public class ToolbarRentView extends BaseCustomView implements RentCityAdapter.CallBack {

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  private CallBack callback;

  @Override
  public void onCityClick(int pos) {
    if (callback != null) {
      callback.onCityClick(pos);
    }
  }

  public interface CallBack {

    void onCityClick(int pos);

  }

  public void setCallback(CallBack callback) {
    this.callback = callback;
  }

  public ToolbarRentView(Context context) {
    super(context);
  }

  public ToolbarRentView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initView() {

  }

  @Override
  protected int getLayoutRes() {
    return R.layout.view_toolbar_rent;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setContent(Object... objects) {
    try {
      if (objects != null && objects.length > 0) {
        ArrayList<RentCity> arrayList = (ArrayList<RentCity>) objects[0];

        RentCityAdapter rentCityAdapter = new RentCityAdapter(arrayList);
        rentCityAdapter.setCallBack(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rentCityAdapter);
        rentCityAdapter.setCallBack(this);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
