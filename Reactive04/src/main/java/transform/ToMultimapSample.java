package transform;

import debug.DebugSingleObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * toMultimap(keySelector) 예제
 */
public class ToMultimapSample {
    public static void main(String[] args) throws InterruptedException {
        Single<Map<String, Collection<Long>>> single =
                // Flowable을 생성
                Flowable.interval(500L, TimeUnit.MILLISECONDS)
                // 5건 까지 통지
                .take(5)
                // 데이터로 생성한 키 각각에 데이터를 담은 Map을 통지
                .toMultimap(data -> {
                    if (data % 2 == 0)
                        return "Even";
                    else
                        return "Odd";
                });

        // 구독
        single.subscribe(new DebugSingleObserver<>());

        // 잠시 기다림
        Thread.sleep(3000L);
    }

    // collctionFactory 구현 예
    // 키를 바탕으로 컬렉션을 생성
    static Function<String, Collection<String>> collectionFactory = key -> {
        if (key.equals("Even"))
            return new HashSet<>();
        else
            return new ArrayList<>();
    };
}
