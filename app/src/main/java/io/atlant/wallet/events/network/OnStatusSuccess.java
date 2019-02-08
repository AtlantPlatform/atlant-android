/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
 */

package io.atlant.wallet.events.network;

import io.atlant.wallet.model.Balance;
import io.atlant.wallet.model.GasPrice;
import io.atlant.wallet.model.Nonce;
import io.atlant.wallet.utils.tokens.Token;

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
