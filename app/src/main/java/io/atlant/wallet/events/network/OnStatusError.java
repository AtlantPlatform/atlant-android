/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.events.network;

public class OnStatusError {

  private int request;

  private String message;

  public OnStatusError(int request) {
    this.request = request;
  }

  public OnStatusError(int request, String message) {
    this.request = request;
    this.message = message;
  }

  public int getRequest() {
    return request;
  }

  public String getMessage() {
    return message;
  }
}
