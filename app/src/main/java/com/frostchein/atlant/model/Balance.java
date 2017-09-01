
package com.frostchein.atlant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balance {

  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("message")
  @Expose
  private String message;
  @SerializedName("result")
  @Expose
  private String result;

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

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

}
