package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * skipUntil(other) 예
 */
public class SkipUntilSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 인자 Flowable이 통지할 때까지 데이터를 통지하지 않음
                .skipUntil(Flowable.timer(1000L, TimeUnit.MILLISECONDS))
                .subscribe(new DebugSubscriber<>());
        Thread.sleep(2000L);
    }
}
