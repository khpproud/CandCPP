package com.patrick.rxstudy08.storio;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.patrick.rxstudy08.StockUpdate;
import com.pushtorefresh.storio3.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio3.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio3.sqlite.queries.UpdateQuery;

/**
 * putResolver 작성
 */
public class StockUpdatePutResolver extends DefaultPutResolver<StockUpdate> {

    // 데이터를 삽입할 테이블을 찾음
    @NonNull
    @Override
    protected InsertQuery mapToInsertQuery(@NonNull StockUpdate object) {
        return InsertQuery.builder()
                .table(StockUpdateTable.TABLE)
                .build();
    }

    // 저장하려는 객체가 이미 데이터베이스에 있는지 조회하는 쿼리
    @NonNull
    @Override
    protected UpdateQuery mapToUpdateQuery(@NonNull StockUpdate object) {
        return UpdateQuery.builder()
                .table(StockUpdateTable.TABLE)
                .where(StockUpdateTable.Columns.ID + "= ?")
                .whereArgs(object.getId())
                .build();
    }

    // 도메인 객체의 값을 SQLite DB가 이해할 수 있는 ContentValues 객체로 변환
    @NonNull
    @Override
    protected ContentValues mapToContentValues(@NonNull StockUpdate object) {
        final ContentValues contentValues = new ContentValues();

        contentValues.put(StockUpdateTable.Columns.ID, object.getId());
        contentValues.put(StockUpdateTable.Columns.STOCK_SYMBOL,
                object.getStockSymbol());
        contentValues.put(StockUpdateTable.Columns.PRICE, getPrice(object));
        contentValues.put(StockUpdateTable.Columns.DATE, getDate(object));

        return contentValues;
    }

    // Date객체를 long으로 변환
    private long getDate(@NonNull StockUpdate entity) {
        return entity.getDate().getTime();
    }

    // BigDecimal로 저장된 가격을 long으로 변환
    private long getPrice(@NonNull StockUpdate entity) {
        return entity.getPrice().scaleByPowerOfTen(4).longValue();
    }
}
