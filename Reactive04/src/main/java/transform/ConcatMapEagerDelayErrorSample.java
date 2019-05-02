package transform;

import debug.DebugTimeSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 * concatMapEagerDelayError(mapper, tillTheEnd) 예제
 */
public class ConcatMapEagerDelayErrorSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.range(10, 3)
                // 받은 데이터로 Flowable을 생성해 바로 실행하나 통지는 받은 순서대로 함
                .concatMapEagerDelayError(
                        // 첫 번째 인자: 통지할 데이터를 가진 Flowable을 생성
                        source -> Flowable.interval(500L, TimeUnit.MILLISECONDS)
                        // 3건 까지
                        .take(3)
                        // '[11] 1'을 통지할 때 예외가 발생하게 함
                        .doOnNext(data -> {
                            if (source == 11 && data == 1) {
                                throw new Exception("예외 발생");
                            }
                        })
                        // 원본 통지 데이터와 이 Flowable의 데이터를 합쳐 문자열을 생성
                        .map(data -> "[" + source + "]" + data),
                        // 두 번째 인자: 에러를 통지할 시점을 설정함
                        true);
        // 구독
        flowable.subscribe(new DebugTimeSubscriber<>());

        // 잠시 기다림
        Thread.sleep(4000L);
    }
}
