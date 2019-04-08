package com.patrick.rxstudy03.stock.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockResult {
    @SerializedName("Global Quote")
    @Expose
    private StockQuote quote;

    public StockQuote getQuote() {
        return quote;
    }
}
