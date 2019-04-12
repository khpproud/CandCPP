package com.patrick.rxstudy11.example;

import android.util.Pair;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;

/**
 *  실행 시간 측정 Transformer
 * @param <R>
 */
public class TimingObservableTransformer<R> implements ObservableTransformer<R, R> {
    // 핸들러로 사용할 Consumer 각 아이템이 방출될 대 액션을 실행, 구독 시작 이후 경과 시간 전달
    private final Consumer<Long> timerAction;

    private TimingObservableTransformer(Consumer<Long> timerAction) {
        this.timerAction = timerAction;
    }

    public static <R> TimingObservableTransformer<R> timeItems(Consumer<Long> timerAction) {
        return new TimingObservableTransformer<>(timerAction);
    }

    @Override
    public ObservableSource<R> apply(Observable<R> upstream) {
        return Observable.combineLatest(
                Observable.just(new Date()),
                upstream,
                Pair::create)
                .doOnNext(pair -> {
                   Date currentTime = new Date();
                   long diff = currentTime.getTime() - pair.first.getTime();
                   long diffSeconds = diff / 1000;

                   timerAction.accept(diffSeconds);
                })
                .map(pair -> pair.second);
    }
}
