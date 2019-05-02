package transform;

import debug.DebugSingleObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * toMap(keySelector, valueSelector, mapSupplier) 예
 */
public class ToMapSample2 {
    public static void main(String[] args) {
        Single<Map<Long, String>> single =
                // Flowable을 생성
                Flowable.just("1A","2B", "3C", "1D", "2E")
                // 데이터에서 생성한 키와 값의 쌍이 담긴 Map을 통지
                .toMap(
                        // 첫 번째 인자 : 키 생성
                        data -> Long.valueOf(data.substring(0, 1)),
                        // 두 번째 인자 : 값 생성
                        data -> data.substring(1),
                        // 세 번째 인자 : 키와 값을 담을 Map 객체를 생성하는 함수형 인터페이스
                        mapSupplier
                );
        // 구독
        single.subscribe(new DebugSingleObserver<>());
    }

    // mapSupplier 구현 예
    static Callable<LinkedHashMap<Long, String>> mapSupplier = LinkedHashMap::new;
}
