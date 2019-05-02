package combination;

import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * merge(source1, source2) 예
 */
public class MergeSample {
    public static void main(String[] args) throws InterruptedException {
        // 병합하는 대상
        Flowable<Long> flowable1 =
                // 300ms 마다 데이터를 통지
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 5건 까지 통지
                .take(5);

        // 병합하는 대상
        Flowable<Long> flowable2 =
                // 500ms 마다 데이터를 통지
                Flowable.interval(500L, TimeUnit.MILLISECONDS)
                // 2건 까지 통지
                .take(2)
                // 100을 더함
                .map(data -> data + 100L);

        // 여러 Flowable을 결합
        Flowable<Long> result = Flowable.merge(flowable1, flowable2);

        // 구독
        result.subscribe(new DebugTimeSubscriber<>());

        // 잠시 기다림
        Thread.sleep(2000L);
    }
}
