package com.frostchein.atlant.events.network;

public class OnStatusTimeOut {

  private int request;

  public OnStatusTimeOut(int request) {
    this.request = request;
  }

  public int getRequest() {
    return request;
  }
}
