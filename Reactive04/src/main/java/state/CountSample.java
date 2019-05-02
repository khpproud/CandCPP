package state;

import debug.DebugSingleObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.concurrent.TimeUnit;

/**
 * count() 예
 */
public class CountSample {
    public static void main(String[] args) throws InterruptedException {
        Single<Long> single = Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                .take(3)
                // 데이터 개수를 통지
                .count();
        single.subscribe(new DebugSingleObserver<>());
        Thread.sleep(4000L);
    }
}
