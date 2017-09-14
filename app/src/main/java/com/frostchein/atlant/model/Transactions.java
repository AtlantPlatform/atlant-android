
package com.frostchein.atlant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Transactions {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private ArrayList<TransactionsItem> transactionsItem = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<TransactionsItem> getTransactionsItem() {
        return transactionsItem;
    }

    public void setTransactionsItem(ArrayList<TransactionsItem> transactionsItem) {
        this.transactionsItem = transactionsItem;
    }

}
