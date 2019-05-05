import io.reactivex.Flowable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * blockingFirst() 테스트
 */
public class BlockingFirstTest {

    @Test
    public void 첫번째통지데이터얻기() {
        // 받아온 결과
        long actual =
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        // 처음 통지된 데이터를 메인 스레드에서 얻음
                        .blockingFirst();
        // 확인
        assertThat(actual, is(0L));
    }
}
