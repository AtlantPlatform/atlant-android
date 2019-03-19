/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import atlant.wallet.R;

public class LoginKeyboardView extends BaseCustomView implements View.OnClickListener {

  private EditText passwordField;

  @BindView(R.id.login_keyboard_key_0)
  protected TextView key0View;
  @BindView(R.id.login_keyboard_key_1)
  protected TextView key1View;
  @BindView(R.id.login_keyboard_key_2)
  protected TextView key2View;
  @BindView(R.id.login_keyboard_key_3)
  protected TextView key3View;
  @BindView(R.id.login_keyboard_key_4)
  protected TextView key4View;
  @BindView(R.id.login_keyboard_key_5)
  protected TextView key5View;
  @BindView(R.id.login_keyboard_key_6)
  protected TextView key6View;
  @BindView(R.id.login_keyboard_key_7)
  protected TextView key7View;
  @BindView(R.id.login_keyboard_key_8)
  protected TextView key8View;
  @BindView(R.id.login_keyboard_key_9)
  protected TextView key9View;
  @BindView(R.id.login_keyboard_key_backspace)
  protected RelativeLayout keyBackspaceView;
  @BindView(R.id.login_keyboard_key_sos)
  protected Button btSOS;

  public LoginKeyboardView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public LoginKeyboardView(Context context) {
    super(context);
  }

  @Override
  protected void initView() {
    key0View.setOnClickListener(this);
    key1View.setOnClickListener(this);
    key2View.setOnClickListener(this);
    key3View.setOnClickListener(this);
    key4View.setOnClickListener(this);
    key5View.setOnClickListener(this);
    key6View.setOnClickListener(this);
    key7View.setOnClickListener(this);
    key8View.setOnClickListener(this);
    key9View.setOnClickListener(this);
    keyBackspaceView.setOnClickListener(this);
    btSOS.setOnClickListener(this);
  }

  @Override
  protected int getLayoutRes() {
    return R.layout.view_login_keyboard;
  }

  @Override
  public void setContent(Object... objects) {
    if (objects != null && objects.length > 0) {
      passwordField = (EditText) objects[0];
    }
  }

  @Override
  public void onClick(View v) {
    if (passwordField != null) {
      switch (v.getId()) {
        case R.id.login_keyboard_key_backspace: {
          Editable editable = passwordField.getText();
          int charCount = editable.length();
          if (charCount > 0) {
            editable.delete(charCount - 1, charCount);
          }
        }
        break;
        case R.id.login_keyboard_key_sos:
          Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/AtlantHelpBot"));
          getContext().startActivity(telegram);
          break;
        default: {
          passwordField.append(((TextView) v).getText());
        }
        break;
      }
    }
  }
}
