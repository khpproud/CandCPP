package aggregate;

import debug.DebugSingleObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

/**
 * reduce(seed,, reducer) 예
 */
public class ReduceSample {
    public static void main(String[] args) {
        Single<Integer> single =
                // 인자의 데이터를 통지하는 Flowable을 생성
                Flowable.just(1, 10, 100, 1000, 10000)
                // reduce 메서드로 받은 데이터를 더한다
                .reduce(0, (sum, data) -> sum + data);
        single.subscribe(new DebugSingleObserver<>());
    }

    // reduce 구현 예
    static BiFunction<Integer, Integer, Integer> reducer = new BiFunction<Integer, Integer, Integer>() {
        /**
         *
         * @param sum 집계 값
         * @param data Flowable/Observable 이 통지한 데이터
         * @return 최종 누적 값
         * @throws Exception
         */
        @Override
        public Integer apply(Integer sum, Integer data) throws Exception {
            return sum + data;
        }
    };
}
