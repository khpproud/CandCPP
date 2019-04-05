package reactivejava2.chap04.transform;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * switchMap() 함수 사용 예
 *
 */
public class SwitchMapExample {
    String[] balls = { "1", "3", "5" };
    public void switchMap() {
        CommonUtils.exampleStart();
        
        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> balls[i])
                .take(balls.length)
                .switchMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .map(notUsed -> ball + "<>")
                        .take(2));
        source.subscribe(Log::it);
        CommonUtils.sleep(2000);
    }
    
    // doOnNext() 추가하여 로그 확인
    public void usingDoOnNext() {
        CommonUtils.exampleStart();
        
        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> balls[i])
                .take(3)
                .doOnNext(Log::dt)
                .switchMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .map(notUsed -> ball + "<>")
                        .take(2));
        source.subscribe(Log::it);
        CommonUtils.sleep(2000);
    }
    
    public static void main(String[] args) {
        SwitchMapExample demo = new SwitchMapExample();
        //demo.switchMap();
        demo.usingDoOnNext();
    }
}
