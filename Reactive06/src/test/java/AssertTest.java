import io.reactivex.Flowable;
import org.junit.Test;

/**
 * assert 메서드를 사용하는 예
 */
public class AssertTest {
    @Test
    public void 빈Flowable을테스트() {
        Flowable.empty()
                //TestSubscriber를 생성
                .test()
                // 데이터를 받지 않으면 성공
                .assertNoValues()
                // 에러가 없으면 성공
                .assertNoErrors()
                // 완료 했으면 성공
                .assertComplete();
    }
}
