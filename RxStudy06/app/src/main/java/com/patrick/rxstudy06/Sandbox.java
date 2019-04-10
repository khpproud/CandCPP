package com.patrick.rxstudy06;

import java.util.concurrent.CountDownLatch;

import io.reactivex.Observable;

import static com.patrick.rxstudy06.LogUtil.plog;

public class Sandbox {
    private static final CountDownLatch WAIT_LATCH = new CountDownLatch(1);

    public static void main(String... args) throws Exception {
        //subscribePar1();
        //onExceptionResumeNext();
        //doOnError();
        onErrorReturnItem();
    }

    // subscribe( , error -> ~~) 두 번째 인자에 에러 처리 추가
    private static void subscribePar1() {
        Observable.just("1")
                .doOnNext(i -> {throw new RuntimeException("Very Wrong!!!");})
                .subscribe(item -> plog("subscribe()", item),
                        LogUtil::plog);
    }

    // onExceptResumeNext() 사용한 예외 처리
    private static void onExceptionResumeNext() {
        Observable.<String>error(new RuntimeException("Crash!"))
                .onExceptionResumeNext(Observable.just("Second"))
                .subscribe(item -> plog("subscribe()", item),
                        LogUtil::plog);
    }

    // doOnError() 사용 subscribe()에 도달하지 않은 오류를 가로챔
    private static void doOnError() {
        Observable.<String>error(new RuntimeException("Crash!"))
                .doOnError(e -> plog("doOnError()", e))
                .onExceptionResumeNext(Observable.just("Second"))
                .subscribe(item -> plog("subscribe()", item),
                        LogUtil::plog);
    }

    // onErrorReturn() 사용 예
    private static void onErrorReturn() {
        Observable.<String>error(new Error("Crash!"))
                .onErrorReturn(throwable -> "Return")
                .subscribe(item -> plog("subscribe()", item),
                        LogUtil::plog);
    }

    // onErrorREturnItem() 사용 예
    private static void onErrorReturnItem() {
        Observable.<String>error(new Error("Crash!!"))
                .onErrorReturnItem("Return")
                .subscribe(item -> plog("subscribe()", item),
                        LogUtil::plog);
    }

    public static int importantLongTask(int i) {
        try {
            long minMillis = 10L;
            long maxMillis = 1000L;
            plog("Working on " + i);
            final long waitingTime = (long) (minMillis + (Math.random() * maxMillis - minMillis));
            Thread.sleep(waitingTime);
            return i;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
