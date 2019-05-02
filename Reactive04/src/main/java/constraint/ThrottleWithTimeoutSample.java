package constraint;

import debug.DebugSubscriber;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * throttleWithTimeout(time, unit) 예
 */
public class ThrottleWithTimeoutSample {
    public static void main(String[] args) {
        Flowable.<String>create(
                // 통지 처리
                emitter -> {
                    // 데이터를 통지하고 잠시 기다림
                    emitter.onNext("A");
                    Thread.sleep(1000L);

                    emitter.onNext("B");
                    Thread.sleep(300L);

                    emitter.onNext("C");
                    Thread.sleep(300L);

                    emitter.onNext("D");
                    Thread.sleep(1000L);

                    emitter.onNext("E");
                    Thread.sleep(100L);

                    // 완료를 통지
                    emitter.onComplete();
                }, BackpressureStrategy.BUFFER)
                // 지정한 기간 안에 다음 데이터가 오지 않으면 데이터를 통지
                .throttleWithTimeout(500L, TimeUnit.MILLISECONDS)
                .subscribe(new DebugSubscriber<>());
    }
}
