package aggregate;

import debug.DebugSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

/**
 * scan(accumulator) 예
 */
public class ScanSample {
    public static void main(String[] args) {
        Flowable.just(1, 10, 100, 1000, 10000)
                // scan 메서드로 받은 데이터를 더함
                .scan(Integer::sum)
                .subscribe(new DebugSubscriber<>());
    }

    // accumulator 구현 예
    static BiFunction<Integer, Integer, Integer> accumulator = new BiFunction<Integer, Integer, Integer>() {
        /**
         *
         * @param sum 집계 값
         * @param data Flowable/Observable 이 통지한 데이터
         * @return 현재 까지의 집계 값
         * @throws Exception
         */
        @Override
        public Integer apply(Integer sum, Integer data) throws Exception {
            return sum + data;
        }
    };
}
