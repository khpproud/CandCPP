package com.patrick.rxstudy06.storio;

import android.content.Context;

import com.patrick.rxstudy06.StockUpdate;
import com.pushtorefresh.storio3.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite;

/**
 * StorIOSQLite 인터페이스 생성 factory class
 */
public class StorIOFactory {
    private static StorIOSQLite sInstance;

    public synchronized static StorIOSQLite get(Context context) {
        if (sInstance != null)
            return sInstance;

        sInstance = DefaultStorIOSQLite.builder()
                // 원본 SQLite DB 에 액세스 할 수 있도록 함
                .sqliteOpenHelper(new StorIODbHelper(context))
                // DB에 매핑 설정
                .addTypeMapping(StockUpdate.class, SQLiteTypeMapping.<StockUpdate>builder()
                        // 쓰기 리졸버 지정
                        .putResolver(new StockUpdatePutResolver())
                        // 읽기 리졸버 지정 (차후 구현)
                        .getResolver(new StockUpdateGetResolver())
                        // 삭제 리졸버 지정 (차후 구현)
                        .deleteResolver(new StockUpdateDeleteResolver())
                        .build()).build();
        return sInstance;
    }
}
