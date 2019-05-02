package creation;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

/**
 * empty() 예제
 */
public class EmptySample {
    public static void main(String[] args) {
        Flowable
                // 빈 Flowable을 생성
                .empty()
                // 구독
                .subscribe(new DebugSubscriber<>());
    }
}
