package reactivejava2.chap04.create;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * interval() 함수 사용 예
 *
 */
public class IntervalExample {
    public void interval() {
        CommonUtils.exampleStart();
        Observable<Long> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(data -> (data + 1) * 100)
                .take(5);
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    // 초기 지연 시간 없이 실행
    public void noInitialDelay() {
        CommonUtils.exampleStart();
        Observable<Long> source = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
                .map(data -> (data + 1) * 100)
                .take(5);
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        IntervalExample demo = new IntervalExample();
        demo.interval();
        demo.noInitialDelay();
    }
}
