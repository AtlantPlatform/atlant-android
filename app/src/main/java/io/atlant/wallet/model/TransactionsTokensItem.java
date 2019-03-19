
/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.wallet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TransactionsTokensItem {

  @SerializedName("address")
  @Expose
  private String address;
  @SerializedName("topics")
  @Expose
  private List<String> topics = null;
  @SerializedName("data")
  @Expose
  private String data;
  @SerializedName("blockNumber")
  @Expose
  private String blockNumber;
  @SerializedName("timeStamp")
  @Expose
  private String timeStamp;
  @SerializedName("gasPrice")
  @Expose
  private String gasPrice;
  @SerializedName("gasUsed")
  @Expose
  private String gasUsed;
  @SerializedName("logIndex")
  @Expose
  private String logIndex;
  @SerializedName("transactionHash")
  @Expose
  private String transactionHash;
  @SerializedName("transactionIndex")
  @Expose
  private String transactionIndex;

  private boolean transactionsIn;

  public boolean isTransactionsIn() {
    return transactionsIn;
  }

  public void setTransactionsIn(boolean transactionsIn) {
    this.transactionsIn = transactionsIn;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<String> getTopics() {
    return topics;
  }

  public void setTopics(List<String> topics) {
    this.topics = topics;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getBlockNumber() {
    return blockNumber;
  }

  public void setBlockNumber(String blockNumber) {
    this.blockNumber = blockNumber;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getGasPrice() {
    return gasPrice;
  }

  public void setGasPrice(String gasPrice) {
    this.gasPrice = gasPrice;
  }

  public String getGasUsed() {
    return gasUsed;
  }

  public void setGasUsed(String gasUsed) {
    this.gasUsed = gasUsed;
  }

  public String getLogIndex() {
    return logIndex;
  }

  public void setLogIndex(String logIndex) {
    this.logIndex = logIndex;
  }

  public String getTransactionHash() {
    return transactionHash;
  }

  public void setTransactionHash(String transactionHash) {
    this.transactionHash = transactionHash;
  }

  public String getTransactionIndex() {
    return transactionIndex;
  }

  public void setTransactionIndex(String transactionIndex) {
    this.transactionIndex = transactionIndex;
  }

}
