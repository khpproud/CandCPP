package utility;

import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

/**
 * repeatWhen(handler) 예
 */
public class RepeatWhenSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.just(1, 2, 3)
                // 반복 통지를 제어
                .repeatWhen(completeHandler -> completeHandler
                        // 통지 시점을 늦춤
                        .delay(1000L, TimeUnit.MILLISECONDS)
                        // 2번 반복
                        .take(2)
                        // 통지 시점에 정보를 출력
                        .doOnNext(data -> System.out.println("emit: " + data))
                        .doOnComplete(() -> System.out.println("complete")))
                // 데이터에 시스템 시각을 추가
                .map(data -> {
                    long time = System.currentTimeMillis();
                    return time + "ms : " + data;
                })
                .subscribe(new DebugTimeSubscriber<>());
        // 잠시 기다림
        Thread.sleep(5000L);
    }

    // handler 구현 예
    Function<Flowable<Object>, Publisher<Object>> handler = new Function<Flowable<Object>, Publisher<Object>>() {
        @Override
        public Publisher<Object> apply(Flowable<Object> completeHandler) throws Exception {
            return completeHandler
                    // 통지 시점을 늦춤
                    .delay(1000L, TimeUnit.MILLISECONDS)
                    // 2번 통지
                    .take(2);
        }
    };
}
