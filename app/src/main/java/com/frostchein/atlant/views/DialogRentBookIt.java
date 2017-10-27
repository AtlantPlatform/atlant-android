package com.frostchein.atlant.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.frostchein.atlant.R;
import com.frostchein.atlant.utils.FontsUtils;

public class DialogRentBookIt extends Dialog {

  @BindView(R.id.name)
  TextView textTitle;
  @BindView(R.id.dialog_message)
  TextView textMessage;
  @BindView(R.id.dialog_bt)
  LinearLayout bt;

  private String message;
  private View.OnClickListener listener;

  public DialogRentBookIt(Context context, String message, View.OnClickListener listener) {
    super(context);
    this.message = message;
    this.listener = listener;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    View view = View.inflate(getContext(), R.layout.dialog_rent_book_it, null);
    ButterKnife.bind(this, view);
    setContentView(view);

    textTitle.setText(getContext().getResources().getString(R.string.app_name));
    FontsUtils.toOctarineBold(getContext(), textTitle);

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

  @OnClick(R.id.rent_dialog_bt_web)
  public void onClickWeb() {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.atlant.io"));
    getContext().startActivity(browserIntent);
  }
}