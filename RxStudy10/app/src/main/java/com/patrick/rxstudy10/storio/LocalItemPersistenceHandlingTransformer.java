package com.patrick.rxstudy10.storio;

import android.content.Context;

import com.patrick.rxstudy10.StockUpdate;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

import static com.patrick.rxstudy10.LogUtil.log;

/**
 *  로컬 DB에 데이터를 저장 및 로드하기 위한 Transformer
 */
public class LocalItemPersistenceHandlingTransformer implements
        ObservableTransformer<StockUpdate, StockUpdate> {
    private final Context context;

    private LocalItemPersistenceHandlingTransformer(Context context) {
        this.context = context;
    }

    public static LocalItemPersistenceHandlingTransformer addLocalItemPersistenceHandling(
            Context context) {
        return new LocalItemPersistenceHandlingTransformer(context);
    }

    @Override
    public ObservableSource<StockUpdate> apply(Observable<StockUpdate> upstream) {
        return upstream.doOnNext(this::saveStockUpdate)
                .onExceptionResumeNext(StorIOFactory
                    .createLocalDbStockUpdateObservable(context));
    }


    // DB에 Stock data 를 저장
    private void saveStockUpdate(StockUpdate stockUpdate) {
        log("saveStockUpdate()", stockUpdate.getStockSymbol());
        StorIOFactory.get(context)
                .put()
                .object(stockUpdate)
                .prepare()
                .asRxSingle()
                .subscribe();
    }
}
