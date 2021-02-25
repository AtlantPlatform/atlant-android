/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import atlant.wallet.R;

public class PasswordView extends BaseCustomView {

  @BindView(R.id.password_layout)
  LinearLayout passwordLayout;
  private int max = 6;

  public PasswordView(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray ta = context.getTheme()
        .obtainStyledAttributes(attrs, R.styleable.PasswordView, 0, 0);
    max = ta.getInt(R.styleable.PasswordView_max, 6);
    ta.recycle();
    initView();
  }

  public PasswordView(Context context) {
    super(context);
  }

  @Override
  protected void initView() {

    int passwordItemSize = (int) getResources().getDimension(R.dimen.password_item_size);
    int passwordItemMargin = (int) getResources().getDimension(R.dimen.password_item_margin);
    for (int i = 0; i < max; ++i) {
      passwordLayout.addView(getItemView(passwordItemSize, passwordItemMargin));
    }
  }

  @Override
  protected int getLayoutRes() {
    return R.layout.view_password;
  }

  @Override
  public void setContent(Object... objects) {

  }

  private View getItemView(int passwordItemSize, int passwordItemMargin) {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(passwordItemSize,
        passwordItemSize);
    layoutParams.setMargins(passwordItemMargin, 0, passwordItemMargin, 0);
    View view = new View(getContext());
    view.setLayoutParams(layoutParams);
    view.setBackgroundResource(R.drawable.login_empty_password_item);
    return view;
  }

  public void fillPasswordItems(int length) {
    for (int position = 0; position < length; ++position) {
      if (max == 6) {
        passwordLayout.getChildAt(position)
            .setBackgroundResource(R.drawable.login_filled_password_item);
      } else {
        passwordLayout.getChildAt(position)
            .setBackgroundResource(R.drawable.login_filled_password_item);
      }
    }
    for (int position = length; position < max; ++position) {
      passwordLayout.getChildAt(position).setBackgroundResource(R.drawable.login_empty_password_item);
    }
  }

  public void emptyPasswordItem(int position) {
    passwordLayout.getChildAt(position).setBackgroundResource(R.drawable.login_empty_password_item);
  }

  public void fillPasswordItem(int position) {
    passwordLayout.getChildAt(position)
        .setBackgroundResource(R.drawable.login_filled_password_item);
  }
}
