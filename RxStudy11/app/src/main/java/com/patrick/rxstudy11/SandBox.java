package com.patrick.rxstudy11;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

import static com.patrick.rxstudy11.LogUtil.*;

// Consumer 이며 동시에 Observable 인 Subject 테스트
public class SandBox {
    private static final CountDownLatch WAIT_LATCH = new CountDownLatch(1);

    public static void main(String... args) throws Exception {
        //publishSubject();
        //publishSubject2();
        //behaviorSubject();
        //replaySubject();
        asyncSubject();

        WAIT_LATCH.await();
    }

    // PublishSubject
    private static void publishSubject() throws InterruptedException {
        Subject<Long> subject = PublishSubject.create();

        Observable.interval(2, TimeUnit.SECONDS)
                .take(5)
                .doOnSubscribe(d -> plog("Original-doOnSubscribe()"))
                .doOnComplete(() -> plog("Original-doOnComplete()"))
                .doOnTerminate(() -> WAIT_LATCH.countDown())
                .subscribe(subject);

        subject.doOnSubscribe(d -> plog("First-doOnSubscribe()"))
                .doOnComplete(() -> plog("First-doOnComplete()"))
                .subscribe(v -> plog("First", v));

        Thread.sleep(4100);

        subject.doOnSubscribe(d -> plog("Second-doOnSubscribe()"))
                .doOnComplete(() -> plog("Second-doOnComplete()"))
                .subscribe(v -> plog("Second", v));
    }

    private static void publishSubject2() throws InterruptedException {
        Subject<Long> subject = PublishSubject.create();

        Observable.interval(2, TimeUnit.SECONDS)
                .take(3)
                .doOnComplete(() -> plog("Original-One-doOnComplete()"))
                .doOnTerminate(WAIT_LATCH::countDown)
                .subscribe(subject);

        Observable.interval(1, TimeUnit.SECONDS)
                .take(2)
                .doOnComplete(() -> plog("Original-Two-doOnComplete()"))
                .subscribe(subject);

        subject.doOnComplete(() -> plog("First-doOnComplete()"))
                .subscribe(v -> plog(v.toString()));
    }

    private static void behaviorSubject() {
        Subject<String> subject = BehaviorSubject.create();

        Observable.interval(0, 2, TimeUnit.SECONDS)
                .take(3)
                .map(v -> "A" + v)
                .doOnTerminate(WAIT_LATCH::countDown)
                .subscribe(subject);

        subject.subscribe(LogUtil::plog);

        Observable.interval(1, 1, TimeUnit.SECONDS)
                .take(3)
                .map(v -> "B" + v)
                .subscribe(subject);
    }

    private static void replaySubject() throws InterruptedException {
        Subject<String> subject = ReplaySubject.create();

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(5)
                .map(Objects::toString)
                .doOnTerminate(WAIT_LATCH::countDown)
                .subscribe(subject);

        Thread.sleep(3100);

        subject.subscribe(LogUtil::plog);

    }

    private static void asyncSubject() throws InterruptedException {
        Subject<String> subject = AsyncSubject.create();

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(5)
                .doOnTerminate(WAIT_LATCH::countDown)
                .map(Objects::toString)
                .subscribe(subject);

        subject.subscribe(LogUtil::plog);

        Thread.sleep(6100);

        subject.subscribe(LogUtil::plog);
    }
}
