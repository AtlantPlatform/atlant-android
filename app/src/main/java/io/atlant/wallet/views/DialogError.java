/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import atlant.wallet.R;

public class DialogError extends Dialog {

  @BindView(R.id.dialog_icon)
  ImageView imageIcon;
  @BindView(R.id.dialog_title)
  TextView textTitle;
  @BindView(R.id.dialog_message)
  TextView textMessage;
  @BindView(R.id.dialog_bt)
  LinearLayout bt;

  private Context context;
  private String title;
  private String message;
  private int iconRes;
  private View.OnClickListener listener;

  public DialogError(Context context, String title, String message, @DrawableRes int iconRes,
      View.OnClickListener listener) {
    super(context);
    this.context = context;
    this.title = title;
    this.message = message;
    this.iconRes = iconRes;
    this.listener = listener;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    View view = View.inflate(getContext(), R.layout.dialog_error, null);
    ButterKnife.bind(this, view);
    setContentView(view);

    imageIcon.setImageDrawable(ContextCompat.getDrawable(context, iconRes));
    textTitle.setText(title);
    textMessage.setText(message);
    bt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.onClick(v);
        }
        dismiss();
      }
    });
  }


}