import debug.DebugSubscriber;
import io.reactivex.Flowable;

/**
 * doOnComplete(onComplete) 예
 */
public class DoOnCompleteSample {
    public static void main(String[] args) {
        Flowable.range(1, 5)
                // 완료 통지시 로그를 출력
                .doOnComplete(() -> System.out.println("doOnComplete"))
                // 구독
                .subscribe(new DebugSubscriber<>());
    }
}
