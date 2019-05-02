package creation;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.time.LocalTime;
import java.util.concurrent.Callable;

/**
 * defer(supplier) 예제
 */
public class DeferSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<LocalTime> flowable = Flowable.defer(() -> Flowable.just(LocalTime.now()));
        Flowable<LocalTime> flowable2 = Flowable.just(LocalTime.now());

         // 구독
        flowable.subscribe(new DebugSubscriber<>("No. 1"));
        flowable2.subscribe(new DebugSubscriber<>("just 1"));

        Thread.sleep(1000L);

        // 구독
        flowable.subscribe(new DebugSubscriber<>("No. 2"));
        flowable2.subscribe(new DebugSubscriber<>("just 2"));
    }

    static Callable<Flowable<LocalTime>> callable = () -> Flowable.just(LocalTime.now());
}
