import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * blockingSubscribe(subscriber) 테스트
 */
public class BlockingSubscribeTest {
    @Test
    public void Flowable을실행하고처리결과확인() {
        Flowable<Long> flowable =
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 5건 까지 통지
                .take(5);

        // 계산 객체
        final Counter counter = new Counter();

        // 부가 작용이 발생하는 처리를 실행
        flowable
                // 현재의 스레드로 구독
                .blockingSubscribe(new DisposableSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        counter.increment();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Assert.fail(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        // 아무것도 하지 않음
                    }
                });
        // Counter의 값을 확인
        Assert.assertThat(counter.get(), CoreMatchers.is(5));
    }
}

// 순서대로 계산하는 클래스
class Counter {
    private volatile int count;

    void increment() {
        count++;
    }

    int get() {
        return count;
    }
}