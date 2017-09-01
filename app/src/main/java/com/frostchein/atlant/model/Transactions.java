
package com.frostchein.atlant.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transactions {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private ArrayList<TransactionItems> transactionItems = null;

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

    public ArrayList<TransactionItems> getTransactionItems() {
        return transactionItems;
    }

    public void setTransactionItems(ArrayList<TransactionItems> transactionItems) {
        this.transactionItems = transactionItems;
    }
}
