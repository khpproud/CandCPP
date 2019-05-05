import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * TestScheduler를 사용한 예
 */
public class TestSchedulerTest {
    @Test
    public void TestScheduler실행() {
        // 테스트 시작 시각
        long start = System.currentTimeMillis();
        // 테스트용 Scheduler
        TestScheduler testScheduler = new TestScheduler();

        // 테스트 대상 Flowable - TestScheduler를 스케줄러로 사용하게 함
        Flowable<Long> flowable = Flowable.interval(500L, TimeUnit.MILLISECONDS, testScheduler);

        // 구독을 시작
        TestSubscriber<Long> result = flowable.test();

        // Scheduler가 진행되지 않으므로 아무것도 출력되지 않음
        System.out.println("data = " + result.values());
        result.assertEmpty();

        // 경과 시간을 현재 시각에서 500ms만 진행
        testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);

        // onNext 이벤트에서 받은 데이터의 리스트
        System.out.println("data = " + result.values());

        // 추가로 500ms 진행 - 이전처리와 함께 1000ms 가 진행됨
        testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);

        // onNext 이벤트에서 받은 데이터의 리스트
        System.out.println("data = " + result.values());
        result.assertValues(0L, 1L);

        // 2000ms 까지 진행
        testScheduler.advanceTimeTo(2000L, TimeUnit.MILLISECONDS);

        // onNext 이벤트에서 받은 데이터의 리스트
        System.out.println("data = " + result.values());
        result.assertValues(0L, 1L, 2L, 3L);

        // 현재 시각 - now 메서드에서 가정한 처리에 걸린 시간을 얻음
        System.out.println("testScheduler#now = " + testScheduler.now(TimeUnit.MILLISECONDS));

        // 테스트에 걸린 시간
        long totalTime = System.currentTimeMillis() - start;
        System.out.println("테스트에 걸린 시간 = " + totalTime);

    }
}
