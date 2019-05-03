package utility;

import debug.DebugTimeSubscriber;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import java.util.concurrent.TimeUnit;

/**
 * timeout(time, unit) 예
 */
public class TimeoutSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.<Integer> create(emitter -> {
            // 1과 2를 통지하고 1200ms를 대기하다가 3을 통지
            emitter.onNext(1);
            emitter.onNext(2);
            // 잠시 기다린다 - 오래걸리는 작업을 처리한다고 설정
            try {
                Thread.sleep(1200L);
            } catch (InterruptedException e) {
                // InterruptedException 이 발생하면 통지하고 완료
                emitter.onError(e);
                return;
            }
            emitter.onNext(3);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER)
                // 1000ms 뒤에 타임아웃이 발생하게 함
                .timeout(1000L, TimeUnit.MILLISECONDS)
                .subscribe(new DebugTimeSubscriber<>());
        Thread.sleep(2000L);
    }

    // 데이터를 받고 1000ms 뒤에 이 데이터를 통지하는 Flowable을 생성
    static Function<Integer, Flowable<Long>> itemTimeoutIndicator = data ->
            Flowable.timer(1000L, TimeUnit.MILLISECONDS);
}
