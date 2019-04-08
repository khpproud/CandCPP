package com.patrick.rxstudy03;

import com.patrick.rxstudy03.stock.json.StockQuote;

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

    public static StockUpdate create(StockQuote sq) {
        return new StockUpdate(sq.getSymbol(), new BigDecimal(sq.getPrice()),
                sq.getLatestTradingDay());
    }
}
