package com.patrick.rxstudy05.storio;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.patrick.rxstudy05.StockUpdate;
import com.pushtorefresh.storio3.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResolver;
import com.pushtorefresh.storio3.sqlite.operations.get.DefaultGetResolver;
import com.pushtorefresh.storio3.sqlite.operations.get.GetResolver;
import com.pushtorefresh.storio3.sqlite.queries.DeleteQuery;

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
                        .getResolver(createGetResolver())
                        // 삭제 리졸버 지정 (차후 구현)
                        .deleteResolver(createDeleteResolver())
                        .build()).build();
        return sInstance;
    }

    private static GetResolver<StockUpdate> createGetResolver() {
        return new DefaultGetResolver<StockUpdate>() {
            @NonNull
            @Override
            public StockUpdate mapFromCursor(@NonNull StorIOSQLite storIOSQLite, @NonNull Cursor cursor) {
                return null;
            }
        };
    }

    private static DeleteResolver<StockUpdate> createDeleteResolver() {
        return new DefaultDeleteResolver<StockUpdate>() {
            @NonNull
            @Override
            protected DeleteQuery mapToDeleteQuery(@NonNull StockUpdate object) {
                return null;
            }
        };
    }
}
