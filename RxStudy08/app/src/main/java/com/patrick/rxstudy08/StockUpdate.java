package com.patrick.rxstudy08;

import com.patrick.rxstudy08.stock.jsonv2.StockData;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 주식 데이터 값 객체 샘플
 */
public class StockUpdate implements Serializable {
    private final String stockSymbol;
    private final BigDecimal price;
    private final Date date;
    private Integer id;                     // DB용 id

    public StockUpdate(String stockSymbol, BigDecimal price, Date date) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.date = date;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public static StockUpdate create(StockData sq) {
        return new StockUpdate(sq.getSymbol(), new BigDecimal(sq.getPrice()),
                sq.getLastTradeTime());
    }
}
