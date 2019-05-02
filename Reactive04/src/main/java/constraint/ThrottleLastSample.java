package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * throttleLast(time, unit) 예
 */
public class ThrottleLastSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 9건 까지 통지
                .take(9)
                // 간격 내 가장 마지막 데이터를 통지
                .throttleLast(1000L, TimeUnit.MILLISECONDS)
                .subscribe(new DebugSubscriber<>());
        Thread.sleep(3000L);
    }
}
