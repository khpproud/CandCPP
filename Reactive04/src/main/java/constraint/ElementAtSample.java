package constraint;

import debug.DebugMaybeObserver;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import java.util.concurrent.TimeUnit;

/**
 * elementAt(index) 예
 */
public class ElementAtSample {
    public static void main(String[] args) throws InterruptedException {
        Maybe<Long> mayBe = Flowable.interval(100L, TimeUnit.MILLISECONDS)
                // 위치가 3(0부터 시작)인 데이터를 통지
                .elementAt(3);

        mayBe.subscribe(new DebugMaybeObserver<>());

        Thread.sleep(1000L);
        // MayBe 는 데이터 통지(onSuccess), 완료 통지(onComplete), 에러 통지(onError) 중 하나만 통지
    }
}
