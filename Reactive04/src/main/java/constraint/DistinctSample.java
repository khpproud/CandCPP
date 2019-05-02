package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * distinct() 예제
 */
public class DistinctSample {
    public static void main(String[] args) {
        Flowable<String> flowable =
                // Flowable을 생성
                Flowable.just("A", "a", "B", "A", "B", "c", "C", "a")
                // 같은 데이터를 제외하고 통지
                //.distinct();
                // 같은 데이터(소문자 대문자 구분 없이) 제외하고 통지
                .distinct(keySelector);

        // 구독
        flowable.subscribe(new DebugSubscriber<>());
    }

    // keySelector 구현 예
    static Function<String, String> keySelector = String::toLowerCase;
}
