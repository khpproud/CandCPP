package com.patrick.rxstudy03.stock.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class StockQuote {
    @SerializedName("01. symbol")
    @Expose
    private String symbol;

    @SerializedName("03. high")
    @Expose
    private Double high;

    @SerializedName("04. low")
    @Expose
    private Double low;

    @SerializedName("05. price")
    @Expose
    private Double price;

    @SerializedName("06. volume")
    @Expose
    private String volume;

    @SerializedName("07. latest trading day")
    @Expose
    private Date latestTradingDay;

    @SerializedName("08. previous close")
    @Expose
    private Double previousClose;

    public String getSymbol() {
        return symbol;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

    public Double getPrice() {
        return price;
    }

    public String getVolume() {
        return volume;
    }

    public Date getLatestTradingDay() {
        return latestTradingDay;
    }

    public Double getPreviousClose() {
        return previousClose;
    }
}
