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
