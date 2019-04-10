package com.patrick.rxstudy08.storio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * StorIO 용 SQLiteOpenHelper 확장
 */
public class StorIODbHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "reactivestock.db";

    public StorIODbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StockUpdateTable.createTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO to be continued...
    }
}
