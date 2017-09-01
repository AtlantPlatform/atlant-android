package com.frostchein.atlant.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.frostchein.atlant.R;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.utils.DigitsUtils;
import java.math.BigInteger;

public class AtlToolbarView extends BaseCustomView {

  @BindView(R.id.toolbar_title)
  TextView textTitle;
  @BindView(R.id.toolbar_value)
  TextView textValue;

  public AtlToolbarView(Context context) {
    super(context);
  }

  public AtlToolbarView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initView() {

  }

  @Override
  protected int getLayoutRes() {
    return R.layout.view_toolbar;
  }

  /**
   * Sets content on view
   *
   * @param objects List of wallet entities.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void setContent(Object... objects) {
    if (objects != null && objects.length > 0) {
      Balance balance = (Balance) objects[0];
      String atl = "0";
      if (balance != null) {
        atl = balance.getResult();
      }
      textValue.setText(
          DigitsUtils.ATLtoString(new BigInteger(atl)) + " " + getResources().getString(R.string.app_prefix_coin));
    }
  }

  public void removeTitle() {
    if (textTitle != null) {
      ((ViewGroup) textTitle.getParent()).removeView(textTitle);
    }
  }
}
