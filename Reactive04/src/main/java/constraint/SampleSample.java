package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * sample(sampler) 예
 */
public class SampleSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 9건 까지 통지
                .take(9)
                // 인자로 받은 Flowable이 데이터를 통지할 때
                // 가장 마지막에 받은 데이터를 통지
                .sample(Flowable.interval(1000L, TimeUnit.MILLISECONDS))
                .subscribe(new DebugSubscriber<>());
        Thread.sleep(3000L);
    }
}
