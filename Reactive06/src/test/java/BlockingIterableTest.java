import io.reactivex.Flowable;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * blockingIterable() 테스트
 */
public class BlockingIterableTest {
    @Test
    public void 통지데이터를얻는Iterable가져오기() throws InterruptedException {
        // 받아온 결과
        Iterable<Long> result =
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 5건 까지 통지
                .take(5)
                // Iterable로 변환해 메인 스레드에서 얻음
                .blockingIterable();
        // Iterator를 가져옴
        Iterator<Long> iterator = result.iterator();

        // 데이터 유무를 확인
        Assert.assertTrue(iterator.hasNext());

        // 데이터를 확인
        Assert.assertThat(iterator.next(), CoreMatchers.is(0L));
        Assert.assertThat(iterator.next(), CoreMatchers.is(1L));
        Assert.assertThat(iterator.next(), CoreMatchers.is(2L));

        // 잠시 기다림, 버퍼 크기를 초과하지 않는 수준
        Thread.sleep(1000L);

        // 데이터를 확인
        Assert.assertThat(iterator.next(), CoreMatchers.is(3L));
        Assert.assertThat(iterator.next(), CoreMatchers.is(4L));

        // 데이터 유무를 확인
        Assert.assertFalse(iterator.hasNext());
    }
}
