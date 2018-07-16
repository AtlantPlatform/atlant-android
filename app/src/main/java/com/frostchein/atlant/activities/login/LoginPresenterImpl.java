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

package com.frostchein.atlant.activities.login;

import android.content.SharedPreferences;
import com.frostchein.atlant.R;
import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.events.login.SuccessfulAuthorisation;
import com.frostchein.atlant.events.login.SuccessfulChangePassword;
import com.frostchein.atlant.events.login.WrongPassword;
import com.frostchein.atlant.utils.CredentialHolder;
import com.frostchein.atlant.utils.DialogUtils;
import com.frostchein.atlant.utils.SharedPreferencesUtils;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginPresenterImpl implements LoginPresenter, BasePresenter {

  private LoginView view;
  private String previousPassword;
  private String password;
  private int typeResult;
  private boolean isSuccessCurrentPassword = false;
  private String privateKey;

  @Inject
  LoginPresenterImpl(LoginView view) {
    this.view = view;
  }

  @Override
  public void onCreate(int typeResult, String privateKey) {
    this.typeResult = typeResult;
    this.privateKey = privateKey;
    if (view != null) {
      if (CredentialHolder.isFileExist(view.getContext(), CredentialHolder.PATH_WALLET)) {
        if (typeResult == LoginActivity.TYPE_AUTHORISATION
            || typeResult == LoginActivity.TYPE_AUTHORISATION_FROM_SELECTED) {
          view.setLoginText(view.getContext().getString(R.string.login_enter_password_text));
        }
        if (typeResult == LoginActivity.TYPE_CHANGE_PASSWORD) {
          view.setLoginText(view.getContext().getString(R.string.login_change_current_password));
        }
        if (typeResult == LoginActivity.TYPE_AUTHORISATION_IMPORT) {
          view.setLoginText(view.getContext().getString(R.string.login_create_password_text));
        }
      } else {
        view.setLoginText(view.getContext().getString(R.string.login_create_password_text));
      }
    }
  }

  @Override
  public void dropLogin() {
    previousPassword = null;
  }

  @Override
  public void onPasswordEntered() {
    if (view != null) {
      password = view.getPassword();

      //change password
      if (isSuccessCurrentPassword && typeResult == LoginActivity.TYPE_CHANGE_PASSWORD) {
        if (previousPassword == null) {
          previousPassword = password;
          view.setLoginText(view.getContext().getString(R.string.login_change_new_repeat_password));
          view.requestPasswordState(true);
        } else {
          if (checkPassword(password)) {
            view.showProgressDialog(view.getContext().getString(R.string.login_dialog_change_wallet));
            CredentialHolder.changePasswordWallet(view.getContext(), password);
          } else {
            view.onWrongPassword();
          }
        }
        //authorisation
      } else {

        //import
        if (typeResult == LoginActivity.TYPE_AUTHORISATION_IMPORT) {
          if (previousPassword == null) {
            previousPassword = password;
            view.setLoginText(view.getContext().getString(R.string.login_confirm_password_text));
            view.requestPasswordState(true);
          } else {
            if (checkPassword(password)) {
              view.showProgressDialog(view.getContext().getString(R.string.login_dialog_create_wallet));
              CredentialHolder.deleteWalletInfo(view.getContext());
              CredentialHolder.createWallet(view.getContext(), privateKey, password);
            } else {
              view.requestPasswordState(false);
              view.onWrongPassword();
            }
          }
          return;
        }

        if (CredentialHolder.isFileExist(view.getContext(), CredentialHolder.PATH_WALLET)) {
          view.showProgressDialog(view.getContext().getString(R.string.login_dialog_decrypt_wallet));
          CredentialHolder.recreateWallet(view.getContext(), password);
        } else {
          if (previousPassword == null) {
            previousPassword = password;
            view.setLoginText(view.getContext().getString(R.string.login_confirm_password_text));
            view.requestPasswordState(true);
          } else {
            if (checkPassword(password)) {
              view.showProgressDialog(view.getContext().getString(R.string.login_dialog_create_wallet));
              if (typeResult == LoginActivity.TYPE_AUTHORISATION_IMPORT) {
                CredentialHolder.changePasswordWallet(view.getContext(), password);
              } else {
                CredentialHolder.createWallet(view.getContext(), password);
                CredentialHolder.saveSetting(view.getContext(), SharedPreferencesUtils.TAG_NEW_PRIVATE_KEY, true);
              }
            } else {
              view.requestPasswordState(false);
              view.onWrongPassword();
            }
          }
        }
      }
    }
  }

  private boolean checkPassword(String password) {
    return password.equals(previousPassword);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onWrongPasswordEvent(WrongPassword event) {
    if (view != null) {
      view.hideProgressDialog();
      view.requestPasswordState(false);
      view.onWrongPassword();
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onSuccessfulAuthorisation(SuccessfulAuthorisation event) {
    if (view != null) {
      view.hideProgressDialog();
      if (previousPassword != null) {
        previousPassword = null;
        view.showProgressDialog(view.getContext().getString(R.string.login_dialog_decrypt_wallet));
        CredentialHolder.createPublicKeyByPrivateKey();
      } else {
        isSuccessCurrentPassword = true;
        SharedPreferences prefs = CredentialHolder.loadSetting(view.getContext());
        CredentialHolder.setNumberToken(view.getContext(), prefs.getInt(SharedPreferencesUtils.TAG_CURRENT_VALUE, -1));
        view.onSuccessfulAuthorisation(password);
      }
    }
  }

  //Change password
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onSuccessfulChangePassword(SuccessfulChangePassword event) {
    if (view != null) {
      DialogUtils.hideDialog();
      view.onSuccessfulChangePassword(password);
    }
  }
}
