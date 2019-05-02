package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

/**
 * distinctUntilChanged() 예
 */
public class DistinctUntilChangedSample1 {
    public static void main(String[] args) {
        Flowable<String> flowable =
                // Flowable을 생성
                Flowable.just("A", "a", "a", "A", "a")
                // 연속된 같은 데이터를 제외하고 통지
                .distinctUntilChanged();
        // 구독
        flowable.subscribe(new DebugSubscriber<>());
    }
}
