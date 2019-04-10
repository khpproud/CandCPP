package com.patrick.rxstudy07.stock.jsonv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class StockData {
    @SerializedName("symbol")
    @Expose
    private String symbol;

    @SerializedName("name")
    @Expose
    private String companyName;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("last_trade_time")
    @Expose
    private Date lastTradeTime;

    @SerializedName("day_high")
    @Expose
    private String priceHigh;

    @SerializedName("day_low")
    @Expose
    private String priceLow;

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPrice() {
        return price;
    }

    public Date getLastTradeTime() {
        return lastTradeTime;
    }

    public String getPriceHigh() {
        return priceHigh;
    }

    public String getPriceLow() {
        return priceLow;
    }
}
