package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.Predicate;

import java.util.concurrent.TimeUnit;

/**
 * takeUntil(stopPredicate) 예
 */
public class TakeUntilSample1 {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable =
                // Flowable을 생성
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 받은 데이터가 '3'이 될때 까지 통지
                .takeUntil(data -> data == 3);
        // 구독
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다림
        Thread.sleep(2000L);
    }

    // stopPredicate 구현 예
    static Predicate<Long> stopPredicate = data -> data == 3;
}
