package reactivejava2.chap04.conditioning;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * skipUntil() 함수 사용 예
 *
 */
public class SkipUntilExample {
    public void skipUntil() {
        String[] data = { "1", "2", "3", "4", "5", "6" };
        
        Observable<String> source = Observable.fromArray(data)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (val, notUsed) -> val)
                .skipUntil(Observable.timer(500L, TimeUnit.MILLISECONDS));
        CommonUtils.exampleStart();
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        SkipUntilExample demo = new SkipUntilExample();
        demo.skipUntil();
    }
}
