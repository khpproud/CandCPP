package com.patrick.rxstudy11;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

import static com.patrick.rxstudy11.LogUtil.*;
import static com.patrick.rxstudy11.LogUtil.log;

/**
 *  Observable 실행 중 로깅을 위한 Transformer
 */
public class LoggerTransformer<R> implements ObservableTransformer<R, R> {
    private final String tag;

    private LoggerTransformer(String tag) {
        this.tag = tag;
    }

    public static <R> LoggerTransformer<R> debugLog(String tag) {
        return new LoggerTransformer<>(tag);
    }


    @Override
    public ObservableSource<R> apply(Observable<R> upstream) {
        return upstream
                .doOnNext(v -> log("doOnNext()", v))
                .doOnError(e -> log("doOnError", e))
                .doOnComplete(() -> log("doOnComplete()", upstream.toString()))
                .doOnTerminate(() -> log("doOnTerminate()", upstream.toString()))
                .doOnDispose(() -> log("doOnDispose()", upstream.toString()))
                .doOnSubscribe(v -> log("doOnSubscribe()", v));
    }
}
