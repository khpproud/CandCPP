package state;

import debug.DebugSingleObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Predicate;

import java.util.concurrent.TimeUnit;

/**
 * all(predicate) 예
 */
public class AllSample {
    public static void main(String[] args) throws InterruptedException {
        Single<Boolean> single = Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                .take(3)
                // 5 이하 인지 판단
                .all(data -> data < 5);
        single.subscribe(new DebugSingleObserver<>());
        Thread.sleep(5000L);
    }

    // 짝수 여부를 판단
    static Predicate<Long> predicate = data -> data % 2 == 0;
}
