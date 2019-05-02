package constraint;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.math.BigDecimal;

/**
 * distinctUntilChanged(comparer) 예
 */
public class DistinctUntilChangedSample2 {
    public static void main(String[] args) {
        Flowable<String> flowable =
                // Flowable을 생성
                Flowable.just("1", "1.0", "0.1", "0.10", "1")
                // 같은 데이터가 연속되면 이 데이터를 제외하고 통지
                .distinctUntilChanged((data1, data2) -> {
                    // BigDecimal 로 변환
                    BigDecimal convert1 = new BigDecimal(data1);
                    BigDecimal convert2 = new BigDecimal(data2);
                    return (convert1.compareTo(convert2) == 0);
                });
        // 구독
        flowable.subscribe(new DebugSubscriber<>());
        // 소수점 자릿수와 관계 없이 바로 앞 데이터와 같은 데이터면 해당 데이터를 통지하지 않음
    }
}
