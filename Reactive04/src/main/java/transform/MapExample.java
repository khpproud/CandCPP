package transform;

import debug.DebugSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import java.math.BigDecimal;

/**
 * map(mapper) 예제
 */
public class MapExample {
    public static void main(String[] args) {
        Flowable<String> flowable =
                // 인자로 받은 데이터를 순서대로 통지하는 Flowable을 생성
                Flowable.just("A", "B", "C", "D", "E")
                // map 메서드를 사용해 소문자로 변환
                .map(String::toLowerCase);
        // 구독
        flowable.subscribe(new DebugSubscriber<>());

        Flowable.just(1, 2, 100, 200)
                .map(function)
                .subscribe(new DebugSubscriber<>());
    }

    // Int 데이터를 BigDecimal로 변환
    static Function<Integer, BigDecimal> function = BigDecimal::new;
}
