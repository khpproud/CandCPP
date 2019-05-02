package transform;

import debug.DebugSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

import java.util.concurrent.TimeUnit;

/**
 * flatMap(mapper, combiner) 예제
 */
public class FlatMapSample2 {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.range(1, 3)
                // mapper와 combiner를 인자로 받는 flatMap 메서드
                .flatMap(
                        // 첫 번째 인자 : 데이터를 받으면 새로운 Flowable을 생성
                        data -> Flowable.interval(100L, TimeUnit.MILLISECONDS).take(3),
                        // 두 번째 인자 : 원본 데이터와 변환한 데이터로 새로운 통지 데이터를 생성
                        combiner
                );
        // 구독
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다림
        Thread.sleep(1000L);
    }

    // combiner 구현 예
    static BiFunction<Integer, Long, String> combiner = new BiFunction<Integer, Long, String>() {
        /**
         *
         * @param source 원본 Flowable/Observable의 데이터
         * @param newData mapper로 생성한 F/O의 데이터
         * @return combiner의 반환 값
         * @throws Exception
         */
        @Override
        public String apply(Integer source, Long newData) throws Exception {
            // 원본 데이터와 mapper의 데이터로 새로운 데이터를 생성
            return "[" + source + "]" + newData;
        }
    };
}
