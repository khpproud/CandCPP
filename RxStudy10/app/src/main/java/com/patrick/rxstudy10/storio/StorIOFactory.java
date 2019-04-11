package com.patrick.rxstudy10.storio;

import android.content.Context;

import com.patrick.rxstudy10.StockUpdate;
import com.pushtorefresh.storio3.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio3.sqlite.queries.Query;

import io.reactivex.Observable;

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

    // Offline 시 로컬 DB에서 정보 가져옴
    public static Observable<StockUpdate> createLocalDbStockUpdateObservable(Context context) {
        return StorIOFactory.get(context)
                // StorIO 에게 SELECT 쿼리 작성을 시작하도록 함
                .get()
                // 반환될 객체 유형 지정
                .listOfObjects(StockUpdate.class)
                // SELECT 쿼리를 만듬
                .withQuery(Query.builder()
                        // 테이블 지정
                        .table(StockUpdateTable.TABLE)
                        // 날짜의 내림차순으로 정렬
                        .orderBy("date DESC")
                        .limit(10)
                        .build())
                // 쿼리 구성이 완료 됐음을 알림
                .prepare()
                .asRxSingle()
                .toObservable()
                .flatMap(Observable::fromIterable);
    }
}
