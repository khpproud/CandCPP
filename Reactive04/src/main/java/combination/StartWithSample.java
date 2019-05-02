package combination;

import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * startWith(other) 예
 */
public class StartWithSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable =
                // 300ms 마다 데이터를 통지
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 5건 까지 통지
                .take(5);

        // 결합 대상
        Flowable<Long> other =
                // 500ms 마다 데이터를 통지
                Flowable.interval(500L, TimeUnit.MILLISECONDS)
                // 2건 까지 통지
                .take(2)
                // 100을 더함
                .map(data -> data + 100);

        // 인자 Flowable을 먼저 실행
        Flowable<Long> result = flowable.startWith(other);
        result.subscribe(new DebugTimeSubscriber<>());
        // 잠시 기다림
        Thread.sleep(3000L);
    }
}
