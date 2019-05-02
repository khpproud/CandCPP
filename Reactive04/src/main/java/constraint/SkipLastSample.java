package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * skipLast(time, unit) 예
 */
public class SkipLastSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(500L, TimeUnit.MILLISECONDS)
                // 5건 까지 통지
                .take(5)
                // 마지막 1000ms 동안 건너뜀
                .skipLast(1000L, TimeUnit.MILLISECONDS)
                .subscribe(new DebugSubscriber<>());
        Thread.sleep(3000L);
    }
}
