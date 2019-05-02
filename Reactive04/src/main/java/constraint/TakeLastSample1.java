package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * takeLat(count) 예
 */
public class TakeLastSample1 {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(800L, TimeUnit.MILLISECONDS)
                // 5건 까지 통지
                .take(5)
                // 마지막 2건을 통지
                .takeLast(2)
                .subscribe(new DebugSubscriber<>());
        Thread.sleep(5000L);
    }
}
