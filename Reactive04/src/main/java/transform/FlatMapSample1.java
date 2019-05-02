package transform;

import debug.DebugSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * flatMap(mapper) 예제
 */
public class FlatMapSample1 {
    public static void main(String[] args) {
        // 인자의 데이터를 통지하는 Flowable을 생성
        Flowable<String> flowable = Flowable.just("A", "", "B", "", "C")
                // flatMap 메서드로 빈 문자를 제거하거나 소문자로 변환
                .flatMap(data -> {
                    if ("".equals(data))
                        // 빈 문자라면 빈 Flowable을 반환
                        return Flowable.empty();
                    else
                        // 소문자로 변환한 데이터가 담긴 Flowable을 반환
                        return Flowable.just(data.toLowerCase());
                });
        // 구독
        flowable.subscribe(new DebugSubscriber<>());

        Flowable.just(1, 3, 5)
                .flatMap(function)
                .subscribe(new DebugSubscriber<>());
    }

    // mapper 구현 예
    static Function<Integer, Flowable<? extends Integer>> function = data -> Flowable.just(data, data * 2);
}
