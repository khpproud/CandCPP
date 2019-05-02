package creation;

import debug.DebugSubscriber;
import io.reactivex.Flowable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

/**
 * fromCallable(supplier) 예제
 */
public class FromCallableSample {
    public static void main(String[] args) {
        // Callable의 결과를 통지하는 Flowable을 생성
        Flowable<Long> flowable = Flowable.fromCallable(callable);

        // 구독
        flowable.subscribe(new DebugSubscriber<>());
    }

    static Callable callable = new Callable() {
        @Override
        public Object call() throws Exception {
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("ss.SSS"));
            return time;
        }
    };
}
