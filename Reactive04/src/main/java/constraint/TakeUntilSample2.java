package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * takeUntil(other) 예
 */
public class TakeUntilSample2 {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 인자 Flwoable이 첫 데이터를 통지할 때 까지 받은 데이터를 통지
                .takeUntil(Flowable.timer(1000L, TimeUnit.MILLISECONDS))
                .subscribe(new DebugSubscriber<>());
        Thread.sleep(2000L);
    }
}
