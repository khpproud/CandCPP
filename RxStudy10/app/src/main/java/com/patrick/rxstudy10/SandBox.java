package com.patrick.rxstudy10;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static com.patrick.rxstudy10.LogUtil.plog;

public class SandBox {
    private static final CountDownLatch WAIT_LATCH = new CountDownLatch(1);

    public static void main(String... args) throws Exception {
        //flatMap();
        switchMap();

        WAIT_LATCH.await();
    }

    private static void flatMap() {
        Observable.interval(3, TimeUnit.SECONDS)
                .take(2)
                .flatMap(x -> Observable.interval(1, TimeUnit.SECONDS)
                    .map(i -> x + "A" + i)
                    .take(5))
                .doOnTerminate(() -> WAIT_LATCH.countDown())
                .subscribe(item -> plog("flatMap", item));

    }

    private static void switchMap() {
        Observable.interval(3, TimeUnit.SECONDS)
                .take(2)
                .switchMap(x -> Observable.interval(1, TimeUnit.SECONDS)
                    .map(i -> x + "A" + i)
                    .take(5))
                .doOnTerminate(WAIT_LATCH::countDown)
                .subscribe(item -> plog("switchMap", item));
    }
}
