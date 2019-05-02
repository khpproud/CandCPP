package transform;

import debug.DebugSingleObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import java.util.Map;

/**
 * toMap(keySelector) 예제
 */
public class ToMapSample {
    public static void main(String[] args) {
        Single<Map<Long, String>> single =
                // Flowable을 생성
                Flowable.just("1A", "2B", "3C", "1D", "2E")
                // 데이터롤 생성한 키와 데이터 쌍을 담음 Map을 통지
        //.toMap(data -> Long.valueOf(data.substring(0, 1)));
        .toMap(keySelector);
        // 구독
        single.subscribe(new DebugSingleObserver<>());
    }

    // keySelector 구현 예
    static Function<String, Long> keySelector = data -> {
        // 첫 1문자를 잘라 Long으로 변환
        return Long.valueOf(data.substring(0, 1));
    };
}
