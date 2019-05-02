package combination;

import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * cincat(source1, source2) 예
 */
public class ConcatSample {
    public static void main(String[] args) throws InterruptedException {
        // 결합 대상
        Flowable<Long> flowable1 =
                // 300ms 마다 데이터를 통지
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 5건 까지 통지
                .take(5);
        // 결합 대상
        Flowable<Long> flowable2 =
                // 500ms 마다 데이터 통지
                Flowable.interval(500L, TimeUnit.MILLISECONDS)
                // 2건 까지 통지
                .take(2)
                // 100을 더함
                .map(data -> data + 100);

        // 여러 Flowable을 결합함
        Flowable<Long> result = Flowable.concat(flowable1, flowable2);

        // 구독
        result.subscribe(new DebugTimeSubscriber<>());
        // 잠시 기다림
        Thread.sleep(3000L);
    }

}
