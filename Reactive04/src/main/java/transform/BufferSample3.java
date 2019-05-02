package transform;

import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * buffer(openingIndicator, closingIndicator) 예제
 */
public class BufferSample3 {
    public static void main(String[] args) throws InterruptedException {
        Flowable<List<Long>> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(7)
                .doOnNext(data -> System.out.println(System.currentTimeMillis() + " : " + data))
                .buffer(Flowable.timer(700L, TimeUnit.MILLISECONDS), closingIndicator);

        // 구독
        flowable.subscribe(new DebugTimeSubscriber<>());

        // 잠시 기다림
        Thread.sleep(2000L);
    }

    //closingIndicator 구현 예
    static Function<Long, Flowable<Long>> closingIndicator = opening -> Flowable.timer(1000L, TimeUnit.MILLISECONDS);
}
