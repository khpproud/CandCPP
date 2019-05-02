package combination;

import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * combineLatest(source1, source2) 예
 */
public class CombineLatestSample {
    public static void main(String[] args) throws InterruptedException {
        // 결합 대상
        Flowable<Long> flowable1 =
                // 300ms 마다 데이터를 통지
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 5건 까지 통지
                .take(5);

        // 결합 대상
        Flowable<Long> flowable2 =
                // 500ms 마다 데이터를 통지
                Flowable.interval(500L, TimeUnit.MILLISECONDS)
                // 3건 까지 통지
                .take(3)
                // 100을 더함
                .map(data -> data + 100L);

        // 복수의 Flowable에서 받은 데이터로 새로운 데이터를 생성
        Flowable<List<Long>> result = Flowable.combineLatest(
                // 첫 번째 인자 : 결합하는 Flowable
                flowable1,
                // 두 번째 인자 : 결합하는 Flowable
                flowable2,
                // 세 번째 인자 : 두 인자가 통지한 데이터를 리스트에 저장하고 이를 통지
                (data1, data2) -> Arrays.asList(data1, data2));
        result.subscribe(new DebugTimeSubscriber<>());
        // 잠시 대기
        Thread.sleep(2000L);
    }

}
