package creation;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

/**
 * range(start, count) 예제
 */
public class RangeSample {
    public static void main(String[] args) {
        // 10부터 순서대로 데이터를 3건을 통지하는 Flowable을 생성
        Flowable<Integer> flowable = Flowable.range(10, 3);

        // 구독
        flowable.subscribe(new DebugSubscriber<>());
    }
}
