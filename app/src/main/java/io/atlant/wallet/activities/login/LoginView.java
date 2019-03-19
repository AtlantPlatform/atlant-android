/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.login;

import io.atlant.wallet.activities.base.BaseView;

public interface LoginView extends BaseView {

  void setLoginText(String text);

  void requestPasswordState(boolean viewState);

  void onWrongPassword();

  void onSuccessfulAuthorisation(String password);

  void onSuccessfulChangePassword(String password);

  String getPassword();
}
