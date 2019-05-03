package utility;

import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * repeatWhen(handler) 예 : 원본 Flowable이 interval 메서도 처럼 다른 스레드에서 처리되는 경우
 */
public class RepeatWhenSample2 {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(100L, TimeUnit.MILLISECONDS)
                // 3건 까지 통지
                .take(3)
                // 반복 통지를 제어
                .repeatWhen(completeHandler -> {
                    return completeHandler
                            // 통지 시점을 늦춤
                            .delay(1000L, TimeUnit.MILLISECONDS)
                            // 2번 반복
                            .take(2)
                            // 통지 시점에 정보를 출력
                            .doOnNext(data -> System.out.println("emit: " + data))
                            .doOnComplete(() -> System.out.println("complete"));
                })
                // 데이터에 시스템 시각을 추가
                .map(data -> {
                    long time = System.currentTimeMillis();
                    return time + "ms : " + data;
                })
                .subscribe(new DebugTimeSubscriber<>());
        // 잠시 기다림
        Thread.sleep(5000L);
    }
}
