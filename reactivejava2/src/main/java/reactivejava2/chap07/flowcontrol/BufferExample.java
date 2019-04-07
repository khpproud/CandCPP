package reactivejava2.chap07.flowcontrol;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * buffer() 함수 사용 예
 *
 */
public class BufferExample {
    String[] data = { "1", "2", "3", "4", "5", "6" };
    public void buffer() {
        CommonUtils.exampleStart();
        
        // 앞의 3개는 100ms 간격으로 발행
        Observable<String> earlySource = Observable.fromArray(data)
                .take(3)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 가운데 1개는 300ms 후에 발행
        Observable<String> middleSource = Observable.just(data[3])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 마지막 2개는 100ms 후에 발행
        Observable<String> lastSource = Observable.just(data[4], data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 3개 씩 모아서 한꺼번에 발행
        Observable<List<String>> source = Observable.concat(earlySource, middleSource, lastSource)
                .buffer(3);
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    // bufferSkip() 사용
    public void bufferSkip() {
        CommonUtils.exampleStart();
        
        // 앞의 3개는 100ms 간격으로 발행
        Observable<String> earlySource = Observable.fromArray(data)
                .take(3)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 가운데 1개는 300ms 후에 발행
        Observable<String> middleSource = Observable.just(data[3])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 마지막 2개는 100ms 후에 발행
        Observable<String> lastSource = Observable.just(data[4], data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 2개는 모으고 1개는 버림
        Observable<List<String>> source = Observable.concat(earlySource, middleSource, lastSource)
                .buffer(2, 3);
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
        
    }
    
    public static void main(String[] args) {
        BufferExample demo = new BufferExample();
        demo.buffer();
        demo.bufferSkip();
    }
}
