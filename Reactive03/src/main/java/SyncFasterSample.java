import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * Intrval 메서드로 생성한 Flowable 예
 * 데이터를 처리하는 속도 보다 통지 하는 측의 속도가 느린 경우
 */
public class SyncFasterSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                // 데이터를 통지할 때의 시스템 시각을 출력
                .doOnNext(data -> System.out.println(Thread.currentThread().getName() +
                        ", emit : " + System.currentTimeMillis() + "ms, " + data))
                .take(3)
                .subscribe(data -> Thread.sleep(500));

        // 잠시 기다림
        Thread.sleep(4000);

    }
}
