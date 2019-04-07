package reactivejava2.chap07.flowcontrol;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * throttleFirst() 함수 사용 예
 *
 */
public class ThrottleFirstExample {
    public void throttleFirst() {
        String[] data = { "1", "2", "3", "4", "5", "6" };
        CommonUtils.exampleStart();
        
        // 앞의 1개는 100ms 간격으로 발행
        Observable<String> earlySource = Observable.just(data[0])
                .zipWith(Observable.timer(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 다음 1개는 300ms 후에 발행
        Observable<String> middleSource = Observable.just(data[1])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 나머지 4개는 100ms 후에 발행
        Observable<String> lastSource = Observable.just(data[2], data[3], data[4], data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
                .doOnNext(Log::dt);
        
        // 200ms 간격으로 throttleFirst() 실행
        Observable<String> source = Observable.concat(earlySource, middleSource, lastSource)
                .throttleFirst(200L, TimeUnit.MILLISECONDS);
        
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        ThrottleFirstExample demo = new ThrottleFirstExample();
        demo.throttleFirst();
    }
}
