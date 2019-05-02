package creation;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

/**
 * just 예제
 */
public class JustSample {
    public static void main(String[] args) {
        // 인자 데이터를 순서대로 통지하는 Flowable을 생성
        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E", "F");

        // 구독을 시작
        flowable.subscribe(new DebugSubscriber<>());
    }
}
