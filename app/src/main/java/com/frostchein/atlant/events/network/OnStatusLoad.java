package com.frostchein.atlant.events.network;

class OnStatusLoad {

  private int request;

  public OnStatusLoad(int request) {
    this.request = request;
  }

  public int getRequest() {
    return request;
  }
}
