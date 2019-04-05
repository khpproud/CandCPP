package reactivejava2.chap04.transform;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * concatMap() 함수 사용 예
 *
 */
public class ConcatMapExample {
    String[] balls = { "1", "3", "5" };
    public void concatMap() {
        // 시간을 측정하기 위해 호출
        CommonUtils.exampleStart();
        
        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> balls[i])
                .take(balls.length)
                .concatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .map(notUsed -> ball +"<>")
                        .take(2));
        source.subscribe(Log::it);
        CommonUtils.sleep(2000);
    }
    
    // concatMap을 flatMap으로 변경했을 시
    public void flatMap() {
        CommonUtils.exampleStart();
        
        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> balls[i])
                .take(balls.length)
                .flatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .map(notUsed -> ball + "<>")
                        .take(2));
        source.subscribe(Log::it);
        CommonUtils.sleep(2000);
    }
    
    public static void main(String[] args) {
        ConcatMapExample demo = new ConcatMapExample();
        //demo.concatMap();
        demo.flatMap();
    }
}
