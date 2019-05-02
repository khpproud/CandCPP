package transform;

import debug.DebugSubscriber;
import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

/**
 * concatMapEager(mapper) 예제
 */
public class ConcatMapEagerSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.range(10, 3)
                // 받은 데이터로 Flowable을 생성하고 바로 실행하지만 통지는 순서대로 된다
                .concatMapEager(
                        // 데이터를 통지할 Flowable을 생성
                        source -> Flowable.interval(500L, TimeUnit.MILLISECONDS)
                        // 2건 까지
                        .take(2)
                        // 원본 통지데이터와 이 Flowable의 데이터를 조합해 문자열을 만듬
                        .map(data -> {
                            // 통지 시 시스템 시각도 데이터에 추가
                            return System.currentTimeMillis() + "ms [" + source +"]" + data;
                        })
                );
        // 구독
        flowable.subscribe(new DebugTimeSubscriber<>());

        // 잠시 기다림
        Thread.sleep(4000L);
    }

    static Function<Long, Publisher<? extends String>> mapper = source ->
            // 400ms 뒤에 0부터 시작하는 숫자를 통지하는 Flowable을 생성
            Flowable.interval(400L, TimeUnit.MILLISECONDS)
            // 원본 Flowable의 데이터와 이 Flowable의 데이터를 조합해 문자열을 만듬
            .map(data -> "[" + source + "]" + data);
}
