package reactivejava2.chap04.transform;

import io.reactivex.Observable;
import reactivejava2.common.Log;

/**
 * 
 * scan() 함수 사용 예
 *
 */
public class ScanExample {
    public void scan() {
        String[] balls = { "1", "3", "5" };
        Observable<String> source = Observable.fromArray(balls)
                .scan((ball1, ball2) -> ball2 + "(" + ball1 + ")");
        source.subscribe(Log::i);
    }
    
    public static void main(String[] args) {
        ScanExample demo = new ScanExample();
        demo.scan();
    }
}
