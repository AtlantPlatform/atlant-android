/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.activities.send;

import io.atlant.wallet.activities.base.BasePresenter;

public interface SendPresenter extends BasePresenter {

  void onCreate(String line);

  void onChangeValue(int pos);

  void refreshContent();

  void onValidate();

  void onSendTransaction();

}
