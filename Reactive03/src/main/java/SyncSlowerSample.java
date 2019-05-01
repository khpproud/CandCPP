import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * 데이터를 받는 측이 무거운 작업을 하는 예제
 */
public class SyncSlowerSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                // 데이터를 통지할 때의 시간을 출력
                .doOnNext(data -> System.out.println(Thread.currentThread().getName()
                        + ", emit : " + System.currentTimeMillis() + "ms, " + data))
                .take(3)
                .subscribe(data -> Thread.sleep(2000L));            // 무거운 처리 작업을 한다고 가정

        // 잠시 기다림
        Thread.sleep(6000L);
    }
}
