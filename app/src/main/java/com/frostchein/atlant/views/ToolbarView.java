package com.frostchein.atlant.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import com.frostchein.atlant.Config;
import com.frostchein.atlant.R;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DigitsUtils;
import com.frostchein.atlant.utils.tokens.Token;
import java.math.BigInteger;

public class ToolbarView extends BaseCustomView {

  @BindView(R.id.toolbar_horizontal_scroll)
  HorizontalScrollView horizontalScrollView;
  @BindView(R.id.toolbar_spinner)
  Spinner spinner;
  @BindView(R.id.toolbar_title)
  TextView textTitle;
  @BindView(R.id.toolbar_value)
  TextView textValue;

  private CallBack callback;
  private int check = 0;
  private int lengthValue = 0;

  public interface CallBack {

    void onItemsClick(int pos);

  }

  public void setCallback(CallBack callback) {
    this.callback = callback;
  }

  public ToolbarView(Context context) {
    super(context);
  }

  public ToolbarView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initView() {
    int color = ContextCompat.getColor(getContext(), R.color.md_white_1000);
    Drawable spinnerDrawable = spinner.getBackground().getConstantState().newDrawable();
    spinnerDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      spinner.setBackground(spinnerDrawable);
    } else {
      spinner.setBackgroundDrawable(spinnerDrawable);
    }

    Token[] tokens = CredentialHolder.getTokens();
    String[] item = new String[tokens.length + 1];
    item[0] = Config.WALLET_ETH;

    for (int i = 0; i < tokens.length; i++) {
      item[1 + i] = tokens[i].getName();
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.view_spinner, item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setSelection(CredentialHolder.getNumberToken() + 1);

    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (callback != null && ++check > 1) {
          callback.onItemsClick(i - 1);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    textValue.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        spinner.performClick();
      }
    });
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
    try {
      if (objects != null && objects.length > 0) {
        Balance balance = (Balance) objects[0];

        String value = null;
        if (balance != null) {
          value = balance.getResult();
        }

        if (value == null || value.isEmpty()) {
          value = "0";
        }

        String s = DigitsUtils.valueToString(new BigInteger(value));
        textValue.setText(s);

        if (s.length() < lengthValue) {
          RelativeLayout.LayoutParams paramsSpinner = new RelativeLayout.LayoutParams(
              ViewGroup.LayoutParams.WRAP_CONTENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
          paramsSpinner.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
          paramsSpinner.addRule(RelativeLayout.CENTER_VERTICAL);
          spinner.setLayoutParams(paramsSpinner);

          RelativeLayout.LayoutParams paramsScroll = new RelativeLayout.LayoutParams(
              ViewGroup.LayoutParams.WRAP_CONTENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
          paramsScroll.addRule(RelativeLayout.LEFT_OF, R.id.toolbar_spinner);
          horizontalScrollView.setLayoutParams(paramsScroll);
        }
        lengthValue = s.length();

        horizontalScrollView.post(new Runnable() {
          @Override
          public void run() {
            if (canScroll(horizontalScrollView)) {
              RelativeLayout.LayoutParams paramsSpinner = new RelativeLayout.LayoutParams(
                  ViewGroup.LayoutParams.WRAP_CONTENT,
                  ViewGroup.LayoutParams.WRAP_CONTENT);
              paramsSpinner.addRule(RelativeLayout.BELOW, R.id.toolbar_horizontal_scroll);
              paramsSpinner.addRule(RelativeLayout.CENTER_HORIZONTAL);
              spinner.setLayoutParams(paramsSpinner);

              RelativeLayout.LayoutParams paramsScroll = new RelativeLayout.LayoutParams(
                  ViewGroup.LayoutParams.WRAP_CONTENT,
                  ViewGroup.LayoutParams.WRAP_CONTENT);
              paramsScroll.addRule(RelativeLayout.CENTER_HORIZONTAL);
              horizontalScrollView.setLayoutParams(paramsScroll);
            }
          }
        });

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean canScroll(HorizontalScrollView scrollView) {
    View child = (View) scrollView.getChildAt(0);
    if (child != null) {
      int childWidth = (child).getWidth();
      return scrollView.getWidth() < childWidth + scrollView.getPaddingLeft() + scrollView.getPaddingRight();
    }
    return false;
  }

  public void removeTitle() {
    if (textTitle != null) {
      ((ViewGroup) textTitle.getParent()).removeView(textTitle);
    }
    if (spinner != null) {
      spinner.setEnabled(false);
      spinner.setBackground(null);
    }
  }
}
