import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * TestSubscriber 사용의 간단한 예
 */
public class TestSubscriberTest {
    @Test
    public void TestSubscriber사용의간단한예제() throws InterruptedException {
        // 대상 Flowable
        Flowable<Long> target = Flowable.interval(300L, TimeUnit.MILLISECONDS);

        // 테스트 Subscriber를 생성하고 flowable의 처리를 시작
        TestSubscriber<Long> testSubscriber = target.test();

        // 아직 데이터가 통지되지 않았느니 확인
        testSubscriber.assertEmpty();

        // 지정한 시간동안 대기하게 함
        // 비동기 처리이므로 300ms로는 통지되지 않을 수도 있음
        testSubscriber.await(400L, TimeUnit.MILLISECONDS);

        // 지금까지 통지된 데이터를 확인
        testSubscriber.assertValue(0L);

        // 지정한 시간동안 대기
        testSubscriber.await(400L, TimeUnit.MILLISECONDS);

        // 지금까지 통지된 데이터를 확인
        testSubscriber.assertValues(0L, 1L);
    }
}
