package com.patrick.rxstudy10.stock.jsonv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockResult {
    @SerializedName("data")
    @Expose
    private List<StockData> datas;

    public List<StockData> getData() { return datas; }
}
