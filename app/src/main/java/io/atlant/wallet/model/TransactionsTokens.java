
/*
 * Copyright 2017-2021 Digital Asset Exchange Limited. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TransactionsTokens {

  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("message")
  @Expose
  private String message;
  @SerializedName("result")
  @Expose
  private ArrayList<TransactionsTokensItem> transactionsTokensItems = null;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ArrayList<TransactionsTokensItem> getTransactionsTokensItems() {
    return transactionsTokensItems;
  }

  public void setTransactionsTokensItems(ArrayList<TransactionsTokensItem> transactionsTokensItems) {
    this.transactionsTokensItems = transactionsTokensItems;
  }
}
