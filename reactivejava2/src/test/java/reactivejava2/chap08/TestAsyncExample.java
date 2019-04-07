package reactivejava2.chap08;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.GsonHelper;
import reactivejava2.common.Log;
import reactivejava2.common.OkHttpHelper;

/**
 * 
 * 비동기 코드 테스트 예
 *
 */
class TestAsyncExample {

    // 테스트 코드를 비활성화 시키는 경우에는 @Disabled을 추가
    @Disabled
    @DisplayName("test Observable.interval() wrong")
    @Test
    void testIntervalWrongWay() {
        Observable<Integer> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(5)
                .map(Long::intValue);
        
        source.doOnNext(Log::d)
        .test().assertResult(0, 1, 2, 3, 4);
    }

    // awaitDone() 함수를 활용한 테스트
    @DisplayName("test Observable.interval() right")
    @Test
    void testInterval() {
        Observable<Integer> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(5)
                .map(Long::intValue);
        
        source.doOnNext(Log::d)
        .test()
        .awaitDone(1L, TimeUnit.SECONDS)
        .assertResult(0, 1, 2, 3, 4);
    }
    
    // Github API를 활용한 HTTP 통신 예
    @DisplayName("http get test")
    @Test
    void testHttp() {
        final String url = "https://api.github.com/users/khpproud";
        Observable<String> source = Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get)
                .doOnNext(Log::d)
                .map(json -> GsonHelper.parseValue(json, "name"))
                .observeOn(Schedulers.newThread());
        String expected = "Patrick";
        source.doOnNext(Log::i)
        .test()
        .awaitDone(3, TimeUnit.SECONDS)
        .assertResult(expected);
    }
}
