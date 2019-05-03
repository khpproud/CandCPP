package utility;

import debug.DebugTimeSubscriber;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * delaySubscription(time, unit) 예
 */
public class DelaySubscriptionSample {
    public static void main(String[] args) throws InterruptedException {
        // 처리 시작 시각을 출력
        System.out.println("처리 시작 : " + System.currentTimeMillis());

        Flowable.<String> create(emitter -> {
            // 구독 시작 시각을 출력
            System.out.println("구독 시작 : " + System.currentTimeMillis());
            // 데이터를 통지
            emitter.onNext("A");
            emitter.onNext("B");
            emitter.onNext("C");
            // 완료를 통지
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER)
                // 처리 시작을 지연
                .delaySubscription(2000L, TimeUnit.MILLISECONDS)
                .subscribe(new DebugTimeSubscriber<>());
        Thread.sleep(3000L);
    }
}
