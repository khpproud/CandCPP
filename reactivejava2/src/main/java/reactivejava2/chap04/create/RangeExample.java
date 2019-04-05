package reactivejava2.chap04.create;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * range() 함수 사용 예
 *
 */
public class RangeExample {
    public void range() {
        Observable<Integer> source = Observable.range(1, 10)
                .filter(number -> number % 2 == 0);
        source.subscribe(Log::i);
    }
    
    // intervalRange() 사용 예
    public void intervalRange() {
        CommonUtils.exampleStart();
        Observable<Long> source = Observable.intervalRange(1, 5, 100L, 100L, TimeUnit.MILLISECONDS);
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    // 함수를 조합해 만든 intervalRange()
    public void composeInterval() {
        CommonUtils.exampleStart();
        Observable<Long> source = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
                .map(data -> data + 1)
                .take(5);
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        RangeExample demo = new RangeExample();
        demo.range();
        demo.intervalRange();
        demo.composeInterval();
    }
}
