package reactivejava2.chap04.combine;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * merge() 함수 사용 예
 *
 */
public class MergeExample {
    public void merge() {
        String[] data1 = { "1", "3" };
        String[] data2 = { "2", "4", "6" };
        
        Observable<String> source1 = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> data1[i])
                .take(data1.length);
        
        Observable<String> source2 = Observable.interval(50L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> data2[i])
                .take(data2.length);
        Observable<String> source = Observable.merge(source1, source2);
        CommonUtils.exampleStart();
        source.subscribe(Log::it);
        
        CommonUtils.sleep(1000);        
    }
    
    public static void main(String[] args) {
        MergeExample demo = new MergeExample();
        demo.merge();
    }
}
