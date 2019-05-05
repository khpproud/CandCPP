import debug.DebugSubscriber;
import io.reactivex.Flowable;

/**
 * doOnNext(onNext) 예제
 */
public class DoOnNextSample {
    public static void main(String[] args) {
        Flowable.range(1, 5)
                // 데이터 통지시 로그를 출력
                .doOnNext(data -> System.out.println("--- 기존 데이터 : " + data))
                // 짝수만 통지
                .filter(data -> data % 2 == 0)
                // 데이터 통지시 로그를 출력
                .doOnNext(data -> System.out.println("--- filter 적용 후 데이터 : " + data))
                // 구독
                .subscribe(new DebugSubscriber<>());
    }
}
