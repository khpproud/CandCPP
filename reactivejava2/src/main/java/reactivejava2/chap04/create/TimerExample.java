package reactivejava2.chap04.create;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * timer() 함수 사용 예
 *
 */
public class TimerExample {
    public void timer() {
        CommonUtils.exampleStart();
        Observable<String> source = Observable.timer(500L, TimeUnit.MILLISECONDS)
                .map(notUsed -> new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        TimerExample demo = new TimerExample();
        demo.timer();
    }
}
