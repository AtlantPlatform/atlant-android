/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.login;

import android.content.SharedPreferences;

import atlant.wallet.R;
import io.atlant.wallet.activities.base.BasePresenter;
import io.atlant.wallet.events.login.SuccessfulAuthorisation;
import io.atlant.wallet.events.login.SuccessfulChangePassword;
import io.atlant.wallet.events.login.WrongPassword;
import io.atlant.wallet.utils.CredentialHolder;
import io.atlant.wallet.utils.DialogUtils;
import io.atlant.wallet.utils.SharedPreferencesUtils;

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
