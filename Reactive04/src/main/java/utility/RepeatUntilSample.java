package utility;

import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * repeatSample(stop) 예
 */
public class RepeatUntilSample {
    public static void main(String[] args) throws InterruptedException {
        // 처리 시작 시간
        final long startTime = System.currentTimeMillis();
        Flowable.interval(100L, TimeUnit.MILLISECONDS)
                // 3건 까지 통지
                .take(3)
                // 통지를 반복
                .repeatUntil(() -> {
                    // 호출 시점에 출력
                    System.out.println("called");
                    // 처리를 시작하고 500ms가 될때까지 반복
                    return System.currentTimeMillis() - startTime > 500L;
                })
                .subscribe(new DebugTimeSubscriber<>());
        Thread.sleep(1000L);
    }
}
