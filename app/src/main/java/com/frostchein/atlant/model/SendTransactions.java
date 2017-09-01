package com.frostchein.atlant.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendTransactions {

  @SerializedName("jsonrpc")
  @Expose
  private String jsonrpc;
  @SerializedName("result")
  @Expose
  private String result;
  @SerializedName("error")
  @Expose
  private ErrorMessage error;
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

  public ErrorMessage getError() {
    return error;
  }

  public void setError(ErrorMessage error) {
    this.error = error;
  }

  public  class ErrorMessage {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Object data;

    public Integer getCode() {
      return code;
    }

    public void setCode(Integer code) {
      this.code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public Object getData() {
      return data;
    }

    public void setData(Object data) {
      this.data = data;
    }

  }

}
