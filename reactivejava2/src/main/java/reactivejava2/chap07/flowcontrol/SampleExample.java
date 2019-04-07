package reactivejava2.chap07.flowcontrol;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * sample() 함수 사용 예
 *
 */
public class SampleExample {
    String[] data = { "1", "7", "2", "3", "6" };
    public void sample() {
        // 시간 측정 용
        CommonUtils.exampleStart();
        
        // 앞의 4대는 100ms 간격으로 발행
        Observable<String> earlySource = Observable.fromArray(data)
                .take(4)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 마지막 데이터는 300ms 후에 발행
        Observable<String> lateSource = Observable.just(data[4])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 2개의 Observable을 결합하고 300ms로 샘플링
        Observable<String> source = Observable.concat(earlySource, lateSource)
                .sample(300L, TimeUnit.MILLISECONDS, true);
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        SampleExample demo = new SampleExample();
        demo.sample();
    }
}
