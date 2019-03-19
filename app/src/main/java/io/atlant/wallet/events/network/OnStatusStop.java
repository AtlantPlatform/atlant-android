/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.events.network;

class OnStatusStop {

  private int request;

  public OnStatusStop(int request) {
    this.request = request;
  }

  public int getRequest() {
    return request;
  }
}
