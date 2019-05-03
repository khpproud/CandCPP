package utility;

import debug.DebugTimeSubscriber;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import java.util.concurrent.TimeUnit;

/**
 * delay(time, unit) 예
 */
public class DelaySample {
    public static void main(String[] args) throws InterruptedException {
        // 처리 시작 시간을 출력
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
                // 통지를 지연
                .delay(2000L, TimeUnit.MILLISECONDS)
                // 통지 시각을 출력
                .doOnNext(data -> System.out.println("통지 시각 : " + System.currentTimeMillis()))
                .subscribe(new DebugTimeSubscriber<>());

        // 잠시 기다림
        Thread.sleep(3000L);

    }

    // itemDelayIndicator 구현 예
    Function<Long, Flowable<Long>> itemDelayIndicator = new Function<Long, Flowable<Long>>() {
        /**
         *
         * @param data 지연을 시키는 함수형 인터페이스의 인자
         * @return
         * @throws Exception
         */
        @Override
        public Flowable<Long> apply(Long data) throws Exception {
            return Flowable.timer(data, TimeUnit.MILLISECONDS);
        }
    };
}
