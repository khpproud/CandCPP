package com.patrick.rxstudy05;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static com.patrick.rxstudy05.LogUtil.plog;

public class Sandbox {
    private static final CountDownLatch WAIT_LATCH = new CountDownLatch(1);

    public static void main(String... args) throws Exception {
        //demo1();
        //demo2();
        //demo3();
        //demo4();
        //demo5();
        //demo6();
        demo7();
    }

    // 동기적인 실행
    private static void demo1() {
        Observable.just("1", "3")
                .doOnNext(i -> plog("doOnNext()", i))
                .subscribe(i -> plog("subscribe()", i));
    }

    // 처리될 스레드를 변경
    private static void demo2() {
        Observable.just("1", "3")
                .doOnNext(i -> plog("onNext()", i))
                .observeOn(Schedulers.computation())
                .subscribe(i -> plog("subscribe()", i));
    }

    // 방출된 순서대로 처리
    private static void demo3() throws Exception {
        Observable.range(1, 500)
                .map(Object::toString)
                .doOnNext(i -> plog("doOnNext()", i))
                .doOnTerminate(WAIT_LATCH::countDown)
                .observeOn(Schedulers.computation())
                .subscribe(i -> plog("subscribe()", i));
        WAIT_LATCH.await();
    }

    private static void demo4() throws Exception {
        Observable.range(1, 100)
                .map(Sandbox::importantLongTask)
                .map(Object::toString)
                .doOnTerminate(WAIT_LATCH::countDown)
                .subscribe(i -> plog("subscribe()", i));
        WAIT_LATCH.await();
    }

    // Executor 스케줄러 사용 예
    private static void demo5() {
        final ExecutorService executor = Executors.newFixedThreadPool(10);
        final Scheduler pooledScheduler = Schedulers.from(executor);

        Observable.range(1, 100)
                .subscribeOn(pooledScheduler)
                .map(Object::toString)
                .subscribe(i -> plog("subscribe()", i));
    }

    private static void demo6() throws InterruptedException {
        final ExecutorService executor = Executors.newFixedThreadPool(100);
        final Scheduler pooledScheduler = Schedulers.from(executor);

        Observable.range(1, 1000)
                .flatMap(i -> Observable.just(i)
                    .subscribeOn(pooledScheduler)
                    .map(Sandbox::importantLongTask))
                .doOnTerminate(WAIT_LATCH::countDown)
                .map(Object::toString)
                .subscribe(i -> plog("subscribe()", i));

        WAIT_LATCH.await();
        executor.shutdown();

    }

    // 스케줄러의 병렬 처리 예
    private static void demo7() throws InterruptedException {
        Observable.range(1, 1000)
                .flatMap(i -> Observable.just(i)
                    .subscribeOn(Schedulers.io())
                    .map(Sandbox::importantLongTask))
                .doOnTerminate(WAIT_LATCH::countDown)
                .map(Objects::toString)
                .subscribe(i -> plog("subscribe()", i));

        WAIT_LATCH.await();
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
