package com.patrick.rxstudy11.storio;

import androidx.annotation.NonNull;

import com.patrick.rxstudy11.StockUpdate;
import com.pushtorefresh.storio3.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio3.sqlite.queries.DeleteQuery;

/**
 * Delete Resolver 작성
 */
public class StockUpdateDeleteResolver extends DefaultDeleteResolver<StockUpdate> {
    @NonNull
    @Override
    protected DeleteQuery mapToDeleteQuery(@NonNull StockUpdate object) {
        return DeleteQuery.builder()
                .table(StockUpdateTable.TABLE)
                .where(StockUpdateTable.Columns.ID + " = ?")
                .whereArgs(object.getId())
                .build();
    }
}
