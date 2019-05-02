package state;

import debug.DebugSingleObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.concurrent.TimeUnit;

/**
 * isEmpty() 예
 */
public class IsEmptySample {
    public static void main(String[] args) throws InterruptedException {
        Single<Boolean> single =
                // Flowable을 생성
                Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                // 3건 까지 통지
                .take(3)
                // 3이상만 통지
                .filter(data -> data >= 3)
                // 통지할 데이터가 있는지 판단
                .isEmpty();
        single.subscribe(new DebugSingleObserver<>());
        Thread.sleep(4000L);
    }
}
