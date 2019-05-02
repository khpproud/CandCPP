package transform;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * buffer(count), buffer(time, unit) 예제
 */
public class BufferSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<List<Long>> flowable =
                // 100ms 마다 숫자를 통지하는 Flowable을 생성
                Flowable.interval(100L, TimeUnit.MILLISECONDS)
                // 10건 까지 통지
                .take(10)
                // 3건 씩 모아 통지
                //.buffer(3);
                // 500ms 간격으로 모아 통지
                .buffer(500L, TimeUnit.MILLISECONDS);
        // 구독
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다림
        Thread.sleep(2000L);
    }
}
