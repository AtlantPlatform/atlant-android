/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.events.network;

class OnStatusLoad {

  private int request;

  public OnStatusLoad(int request) {
    this.request = request;
  }

  public int getRequest() {
    return request;
  }
}
