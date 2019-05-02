package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * throttleFirst(time, unit) 예
 */
public class ThrottleFirstSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 10건 까지 통지
                .take(10)
                // 지정 시간 안에는 다음 데이터를 통지하지 않음
                .throttleFirst(1000L, TimeUnit.MILLISECONDS)
                .subscribe(new DebugSubscriber<>());
        Thread.sleep(4000L);
    }
}
