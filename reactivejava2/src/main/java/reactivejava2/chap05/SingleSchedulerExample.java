package reactivejava2.chap05;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * 싱글 스레드 스케줄러 사용 예
 *
 */
public class SingleSchedulerExample {
    public void single() {
        Observable<Integer> numbers = Observable.range(100, 5);
        Observable<String> chars = Observable.range(0, 5)
                .map(CommonUtils::numberToAlphabet); 
        
        numbers.subscribeOn(Schedulers.single())
        .subscribe(Log::i);
        
        chars.subscribeOn(Schedulers.single())
        .subscribe(Log::i);
        CommonUtils.sleep(500);
    }
    public static void main(String[] args) {
        SingleSchedulerExample demo = new SingleSchedulerExample();
        demo.single();
    }
}
