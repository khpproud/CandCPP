package combination;

import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * zip(source1, source2) 예
 */
public class ZipSample {
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
                .map(data -> data + 100);

        // 여러 개의 Flowable에서 받은 데이터로 새로운 데이터를 생성
        Flowable<List<Long>> result = Flowable.zip(
                // 결합하는 Flowable
                flowable1,
                // 결합하는 Flowable
                flowable2,
                // 인자에서 통지한 데이터를 리스트에 저장하고 통지
                (data1, data2) -> Arrays.asList(data1, data2));
        result.subscribe(new DebugTimeSubscriber<>());
        // 잠시 기다림
        Thread.sleep(2000L);

        // 데이터 개수가 적은 Flowable의 완료 시점에 완료를 통지하고 개수가 많은 Flowable의 초과 데이터는 무시됨
    }

    // zipper 구현 예제
    static BiFunction<Long, Long, List<Long>> zipper = (data1, data2) -> Arrays.asList(data1, data2);
}
