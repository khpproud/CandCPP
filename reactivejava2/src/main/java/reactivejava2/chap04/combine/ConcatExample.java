package reactivejava2.chap04.combine;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * concat() 함수 사용 예
 *
 */
public class ConcatExample {
    public void concat() {
        // onComplete 로그 확인 용
        Action onCompleteAction = () -> Log.d("onComplete");
        
        String[] data1 = { "1", "3", "5" };
        String[] data2 = { "2", "4", "6" };
        
        Observable<String> source1 = Observable.fromArray(data1)
                .doOnComplete(onCompleteAction);
        Observable<String> source2 = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> data2[i])
                .take(data2.length)
                .doOnComplete(onCompleteAction);
        
        Observable<String> source = Observable.concat(source1, source2)
                .doOnComplete(onCompleteAction);
        CommonUtils.exampleStart();
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        ConcatExample demo = new ConcatExample();
        demo.concat();
    }
}
