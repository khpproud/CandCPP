package reactivejava2.chap04.etc;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * delay() 함수 활용 예
 *
 */
public class DelayExample {
    public void delay() {
        String[] data = { "1", "7", "2", "3", "4" };
        Observable<String> source = Observable.fromArray(data)
                .delay(100L, TimeUnit.MILLISECONDS);
        CommonUtils.exampleStart();
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        DelayExample demo = new DelayExample();
        demo.delay();
    }
}
