package transform;

import debug.DebugSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;

import java.util.concurrent.Callable;

/**
 * flatMap(onNextMapper, onErrorMapper, onCompleteSupplier) 예제
 */
public class FlatMapSample3 {
    public static void main(String[] args) {
        // 에러가 발생할 Flowable
        Flowable<Integer> original = Flowable.just(1, 2, 0, 4, 5)
                // 0이 들어오면 예외가 발생
                .map(data -> 10 / data);

        // 일반 통지시, 에러 발생시, 완료 시 각각 설정한 데이터로 변환
        original.flatMap(
                // onNextMapper
                onNextMapper,
                // onErrorMapper
                onErrorMapper,
                // onCompleteSupplier
                onCompleteSupplier
        ).subscribe(new DebugSubscriber<>());
    }

    // onNextMapper 구현 예
    static Function<Integer, Publisher<? extends Integer>> onNextMapper = data -> Flowable.just(data, data * 2);

    // onErrorMapper 구현 예
    static Function<Throwable, Publisher<? extends Integer>> onErrorMapper = t -> {
        if (t instanceof ArithmeticException) {
            // 에러가 ArithmeticException 이면 -1을 통지
            return Flowable.just(-1);
        } else {
            return Flowable.error(t);
        }
    };

    // onCompleteSupplier 구현 예
    static Callable<Publisher<? extends Integer>> onCompleteSupplier = () -> Flowable.just(100);
}
