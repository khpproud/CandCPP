package creation;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.Arrays;

/**
 * fromArray / fromIterable 예제
 */
public class FromArrayIterableSample {
    public static void main(String[] args) {
        // 배열 데이터를 순서대로 통지하는 Flowable을 생성
        Flowable<String> flowable = Flowable.fromArray("a", "b", "c", "d", "e");

        // 구독
        flowable.subscribe(new DebugSubscriber<>());

        // 리스트(Iterable) 데이터를 순서대로 통지하는 Flowable을 생성
        Flowable<Integer> flowable2 = Flowable.fromIterable(Arrays.asList(1 ,2 ,3, 4, 5));

        // 구독
        flowable2.subscribe(new DebugSubscriber<>());
    }
}
