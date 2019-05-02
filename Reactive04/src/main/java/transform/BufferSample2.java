package transform;

import debug.DebugSubscriber;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * buffer(boundaryIndicatorSupplier) 예쩨
 */
public class BufferSample2 {
    public static void main(String[] args) throws InterruptedException {
        Flowable<List<Long>> flowable =
                // 300ms 마다 숫자를 통지하는 Flowable
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 7건 까지 통지
                .take(7)
                // 버퍼 구간을 나누는 Flowable을 생성
                .buffer(boundaryIndicator);

        // 구독
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다림
        Thread.sleep(4000L);
    }

    // boundaryIndicatorSupplier 구현 예
    static Callable<? extends Publisher<Long>> boundaryIndicator = () -> Flowable.timer(1000L, TimeUnit.MILLISECONDS);
}
