package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * takeLast(count, time, unit) 예
 */
public class TakeLastSample2 {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 10건 까지 통지
                .take(10)
                // 완료 전 1000ms 동안 통지된 데이터 중 끝에서부터 2건을 통지
                .takeLast(2, 1000L, TimeUnit.MILLISECONDS)
                .subscribe(new DebugSubscriber<>());
        Thread.sleep(4000L);
    }
}
