package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * takeWhile(predicate) 예
 */
public class TakeWhileSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 받은 데이터가 '3'이 아닐 때만 통지
                .takeWhile(data -> data != 3)
                .subscribe(new DebugSubscriber<>());
        Thread.sleep(2000);
    }
}
