package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * skipWhile(predicate) 예
 */
public class SkipWhileSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 받은 데이터가 3이 아닐 때는 데이터를 통지하지 않음
                .skipWhile(data -> data != 3)
                .subscribe(new DebugSubscriber<>());
        Thread.sleep(2000L);
    }
}
