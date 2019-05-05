import io.reactivex.Flowable;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * blockingLast() 테스트
 */
public class BlockingLastTest {
    @Test
    public void 마지막통지데이터받기() {
        // 받아온 결과
        long actual =
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 3건 까지 통지
                .take(3)
                // 아지막 통지데이토를 메인 스레드에서 얻음
                .blockingLast();
        // 확인
        Assert.assertThat(actual, CoreMatchers.is(2L));
    }
}
