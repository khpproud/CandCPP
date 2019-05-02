package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * filter(predicate) 예제
 */
public class FilterSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable =
                // Flowable을 생성
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 짝수만 통지
                .filter(data -> data % 2 == 0);
        // 구독
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다림
        Thread.sleep(3000L);
    }
}
