package com.frostchein.atlant.events.network;

import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.GasPrice;
import com.frostchein.atlant.model.Nonce;
import com.frostchein.atlant.model.Transactions;

public class OnStatusSuccess {

  private int request;
  private Balance balance;
  private Transactions transactions;
  private Nonce nonce;
  private GasPrice gasPrice;

  public OnStatusSuccess(int request) {
    this.request = request;
  }

  public OnStatusSuccess(int request, Balance balance, Transactions transactions) {
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

  public Balance getBalance() {
    return balance;
  }

  public void setBalance(Balance balance) {
    this.balance = balance;
  }

  public Transactions getTransactions() {
    return transactions;
  }

  public void setTransactions(Transactions transactions) {
    this.transactions = transactions;
  }

  public Nonce getNonce() {
    return nonce;
  }

  public GasPrice getGasPrice() {
    return gasPrice;
  }
}
