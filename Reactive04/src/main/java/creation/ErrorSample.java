package creation;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.time.LocalTime;
import java.util.concurrent.Callable;

/**
 * error(throwable) 예제
 */
public class ErrorSample {
    public static void main(String[] args) {
        Flowable
                // 에러를 통지하는 Flowable을 생성
                .error(new Exception("예외 발생!!!"))
                .subscribe(new DebugSubscriber<>());
    }

    // 호출되면 에러 객체를 생성
    Callable<IllegalArgumentException> callable = () -> new IllegalArgumentException("발생 시각 : " + LocalTime.now());
}
