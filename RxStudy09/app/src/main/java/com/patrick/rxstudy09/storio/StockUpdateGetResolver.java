package com.patrick.rxstudy09.storio;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.patrick.rxstudy09.StockUpdate;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.get.DefaultGetResolver;

import java.math.BigDecimal;
import java.util.Date;

/**
 * GetResolver 작성
 */
public class StockUpdateGetResolver extends DefaultGetResolver<StockUpdate> {
    @NonNull
    @Override
    public StockUpdate mapFromCursor(@NonNull StorIOSQLite storIOSQLite, @NonNull Cursor cursor) {
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow(StockUpdateTable.Columns.ID));
        final long dateLong = cursor.getLong(
                cursor.getColumnIndexOrThrow(StockUpdateTable.Columns.DATE));
        final long priceLong = cursor.getLong(
                cursor.getColumnIndexOrThrow(StockUpdateTable.Columns.PRICE));
        final String stockSymbol = cursor.getString(
                cursor.getColumnIndexOrThrow(StockUpdateTable.Columns.STOCK_SYMBOL));
        final String twitterStatus = cursor.getString(
                cursor.getColumnIndexOrThrow(StockUpdateTable.Columns.TWITTER_STATUS));
        Date date = getDate(dateLong);
        BigDecimal price = getPrice(priceLong);

        final StockUpdate stockUpdate = new StockUpdate(stockSymbol, price, date, twitterStatus);
        stockUpdate.setId(id);
        return stockUpdate;
    }

    private Date getDate(long dateLong) {
        return new Date(dateLong);
    }

    // long을 BigDecimal로 변환(소수점 네자리 까지 값을 조정 했으므로 역순)
    private BigDecimal getPrice(long priceLong) {
        return new BigDecimal(priceLong).scaleByPowerOfTen(-4);
    }
}
