package com.frostchein.atlant.events.network;

class OnStatusStop {

  private int request;

  public OnStatusStop(int request) {
    this.request = request;
  }

  public int getRequest() {
    return request;
  }
}
