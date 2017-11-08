package com.frostchein.atlant.events.network;

import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.GasPrice;
import com.frostchein.atlant.model.Nonce;
import com.frostchein.atlant.utils.tokens.Token;

public class OnStatusSuccess {

  private int request;
  private Balance balance;
  private Token token;
  private Object transactions;
  private Nonce nonce;
  private GasPrice gasPrice;

  public OnStatusSuccess(int request) {
    this.request = request;
  }

  public OnStatusSuccess(int request, Balance balance, Object transactions, Token token) {
    this.request = request;
    this.balance = balance;
    this.token = token;
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

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }
}
