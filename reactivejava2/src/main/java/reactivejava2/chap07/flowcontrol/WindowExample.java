package reactivejava2.chap07.flowcontrol;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * window() 함수 사용 예
 *
 */
public class WindowExample {
    public void window() {
        String[] data = { "1", "2", "3", "4", "5", "6" };
        CommonUtils.exampleStart();
        
        // 앞의 3대는 100ms 간격으로 발행
        Observable<String> earlySource = Observable.fromArray(data)
                .take(3)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 가운데 1개는 300ms 후에 발행
        Observable<String> middleSource = Observable.just(data[3])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 마지막 2개는 100ms 후에 발행
        Observable<String> lastSource = Observable.just(data[4], data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 데이터 3개씩 모아서 새로운 Observable을 생성함
        Observable<Observable<String>> source = Observable.concat(earlySource, middleSource, lastSource)
                .window(3);
        
        source.subscribe(observable -> {
           Log.dt("New Observable started!!");
           observable.subscribe(Log::it);
        });
        
        CommonUtils.sleep(1000);
        CommonUtils.exampleComplete();
    }
    
    public static void main(String[] args) {
        WindowExample demo = new WindowExample();
        demo.window();
    }
}
