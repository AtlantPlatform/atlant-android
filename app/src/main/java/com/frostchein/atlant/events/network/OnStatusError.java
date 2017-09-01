package com.frostchein.atlant.events.network;

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
