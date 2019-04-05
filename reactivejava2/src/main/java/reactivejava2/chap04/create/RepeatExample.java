package reactivejava2.chap04.create;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;
import reactivejava2.common.OkHttpHelper;

/**
 * 
 * repeat() 함수 사용 예
 *
 */
public class RepeatExample {
    String serverUrl = "https://api.github.com/zen";
    
    public void repeat() {
        String[] balls = { "1", "3", "5" };
        // 3번 반복해서 발행
        Observable<String> source = Observable.fromArray(balls)
                .repeat(3);
        source.doOnComplete(() -> Log.d("onComplete"))
        .subscribe(Log::i);
    }
    
    // heart beat 구현 예
    public void heartbeatV1() {
        CommonUtils.exampleStart();    
        
        // 2초 간격으로 서버에 ping 보내기
        Observable.timer(2, TimeUnit.SECONDS)
        .map(val -> serverUrl)
        .map(OkHttpHelper::get)
        .repeat()
        .subscribe(res -> Log.it("Ping result : " + res));
        CommonUtils.sleep(10000);
    }
    
    // interval로 구현(동일한 스레드에서 실행)
    public void heartbeatSameThread() {
        CommonUtils.exampleStart();
        
        Observable.interval(2, TimeUnit.SECONDS)
        .map(val -> serverUrl)
        .map(OkHttpHelper::get)
        .subscribe(res -> Log.it("Ping result : " + res));
        CommonUtils.sleep(10000);
    }
    
    public static void main(String[] args) {
        RepeatExample demo = new RepeatExample();
        demo.repeat();
        //demo.heartbeatV1();
        demo.heartbeatSameThread();
    }
}
