package state;

import debug.DebugSingleObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.concurrent.TimeUnit;

/**
 * contains(item) 예
 */
public class ContainsSample {
    public static void main(String[] args) throws InterruptedException {
        Single<Boolean> single = Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                .contains(3L);

        // 구독
        single.subscribe(new DebugSingleObserver<>());
        Thread.sleep(5000L);
    }
}
