package transform;

import debug.DebugSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

/**
 * concatMap(mapper) 예제
 */
public class ConcatMapSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.range(10, 3)
                // 받은 데이터를 기반으로 통지할 데이터를 가진 Flowable을 생성해 순서대로 실행
                .concatMap(
                        // 통지할 데이터가 있는 Flowable을 생성
                        source -> Flowable.interval(500L, TimeUnit.MILLISECONDS)
                        // 2건 까지 통지
                        .take(2)
                        // 원본 통지 데이터와 결과 Flowable의 데이터를 조합해 문자열을 만듬
                        .map(data -> {
                            // 통지 시의 시각도 데이터에 추가
                            long time = System.currentTimeMillis();
                            return time + "ms : [" + source + "]" + data;
                        })
                );
        // 구독
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다림
        Thread.sleep(4000L);
    }

    // mapper 구현 예
    Function<Long, Publisher<? extends String>> mapper = source -> {
        // 400ms 뒤에 0부터 시작하는 숫자를 통지하는 Flowable을 생성
        return Flowable.interval(400L, TimeUnit.MILLISECONDS)
                // 원본 Flowable 데이터와 interval로 생성한 Flowable 데이터를 합쳐 문자열을 생성
                .map(data -> "[" + source +" ]" + data);
    };
}
