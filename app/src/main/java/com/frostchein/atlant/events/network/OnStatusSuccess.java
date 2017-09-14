package com.frostchein.atlant.events.network;

import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.GasPrice;
import com.frostchein.atlant.model.Nonce;

public class OnStatusSuccess {

  private int request;
  private Balance balance;
  private Object transactions;
  private Nonce nonce;
  private GasPrice gasPrice;

  public OnStatusSuccess(int request) {
    this.request = request;
  }

  public OnStatusSuccess(int request, Balance balance, Object transactions) {
    this.request = request;
    this.balance = balance;
    this.transactions = transactions;
  }

  public OnStatusSuccess(int request, Nonce nonce, GasPrice gasPrice) {
    this.request = request;
    this.nonce = nonce;
    this.gasPrice = gasPrice;
  }

  public int getRequest() {
    return request;
  }

  public void setRequest(int request) {
    this.request = request;
  }

  public Balance getBalance() {
    return balance;
  }

  public void setBalance(Balance balance) {
    this.balance = balance;
  }

  public Object getTransactions() {
    return transactions;
  }

  public void setTransactions(Object transactions) {
    this.transactions = transactions;
  }

  public Nonce getNonce() {
    return nonce;
  }

  public void setNonce(Nonce nonce) {
    this.nonce = nonce;
  }

  public GasPrice getGasPrice() {
    return gasPrice;
  }

  public void setGasPrice(GasPrice gasPrice) {
    this.gasPrice = gasPrice;
  }
}
