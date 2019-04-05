package reactivejava2.chap02;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import reactivejava2.common.CommonUtils;

/**
 * 
 * ConnectableObservable 클래스 사용 예
 *
 */
public class ConnectableObservableExample {
    public void publish() {
        String[] dt = { "1", "3", "5" };
        
        // 100ms 간격으로 data를 발행
        Observable<String> balls = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> dt[i])
                .take(dt.length);
        ConnectableObservable<String> source = balls.publish();
        source.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        source.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        source.connect();
        
        CommonUtils.sleep(2500);
        source.subscribe(data -> System.out.println("Subscriber #3 => " + data));
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        ConnectableObservableExample demo = new ConnectableObservableExample();
        demo.publish();
    }
}
