import debug.DebugSubscriber;
import io.reactivex.Flowable;

/**
 * doOnError(onError) 예
 */
public class DoOnErrorSample {
    public static void main(String[] args) {
        Flowable.range(1, 5)
                // 에러 통지시 로그를 출력
                .doOnError(error -> System.err.println("기존 데이터 : " + error.getMessage()))
                // 데이터가 3일 때 에러가 발생
                .map(data -> {
                    if (data == 3)
                        throw new Exception("에러 발생");
                    return data;
                })
                // 에러 통지시 로그를 출력
                .doOnError(error -> System.err.println("--- map 적용 후 : " + error.getMessage()))
                // 구독
                .subscribe(new DebugSubscriber<>());
    }
}
