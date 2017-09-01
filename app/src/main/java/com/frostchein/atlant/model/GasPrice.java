package com.frostchein.atlant.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GasPrice {

  @SerializedName("jsonrpc")
  @Expose
  private String jsonrpc;
  @SerializedName("result")
  @Expose
  private String result;
  @SerializedName("id")
  @Expose
  private Integer id;

  public String getJsonrpc() {
    return jsonrpc;
  }

  public void setJsonrpc(String jsonrpc) {
    this.jsonrpc = jsonrpc;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
