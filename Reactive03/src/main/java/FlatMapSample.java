import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * flatMap 메서드에서 서로 다른 스레드로 작동하는 Flowable을 생성하는 예
 * 순서가 보장되지 않음
 */
public class FlatMapSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable =
                // Flowable을 생성
                Flowable.just("A", "B", "C")
                // 받은 데이터로 Flowable을 생성하고 이 Flowable의 데이터를 통지
                .flatMap(data -> {
                    // 1000ms 늦게 데이터를 통지하는 Flowable을 생성
                    return Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS);
                });

        // 구독
        flowable.subscribe(data -> System.out.println(Thread.currentThread().getName() + " : " + data));

        // 잠시 기다림
        Thread.sleep(2000L);
    }
}
