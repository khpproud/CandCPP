package state;

import debug.DebugSingleObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.BiPredicate;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * sequenceEqual(source1, source2) 예
 */
public class SequenceEqualSample {
    public static void main(String[] args) throws InterruptedException {
        // 비교 대상
        Flowable<Long> flowable1 = Flowable.interval(1000L, TimeUnit.MILLISECONDS).take(3);
        // 비교 대상
        Flowable<Long> flowable2 = Flowable.just(0L, 1L, 2L);

        // 같은 데이터가 같은 순서로 같은 수만큼 있는지를 판단
        Single<Boolean> single = Flowable.sequenceEqual(flowable1, flowable2);
        single.subscribe(new DebugSingleObserver<>());
        Thread.sleep(4000L);

    }

    // isEqual 구현 예
    static BiPredicate<BigDecimal, BigDecimal> isEqual = (data1, data2) -> {
        // 크기와 상관 없이 같은지를 판단
        return data1.compareTo(data2) == 0;
    };
}
