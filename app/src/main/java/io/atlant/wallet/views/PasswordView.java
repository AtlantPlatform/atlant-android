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
