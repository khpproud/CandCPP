package reactivejava2.chap04.etc;

import io.reactivex.Observable;
import io.reactivex.schedulers.Timed;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * timeInterval() 함수 활용 예
 *
 */
public class TimeIntervalExample {
    public void timeInterval() {
        String[] data = { "1", "3", "7" };
        
        CommonUtils.exampleStart();
        Observable<Timed<String>> source = Observable.fromArray(data)
                .delay(item -> {
                    CommonUtils.doSomething();
                    return Observable.just(item);
                })
                .timeInterval();
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        TimeIntervalExample demo = new TimeIntervalExample();
        demo.timeInterval();
    }
    
}
