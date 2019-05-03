package utility;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

/**
 * repeat(long) 예
 */
public class RepeatSample {
    public static void main(String[] args) {
        Flowable.just("A", "B", "C")
                // 통지를 2회 반복
                .repeat(2)
                .subscribe(new DebugSubscriber<>());
    }
}
