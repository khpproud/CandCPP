package reactivejava2.chap04.combine;

import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;
import reactivejava2.common.Shape;

import static reactivejava2.common.Shape.*;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * 
 * combineLatest() 함수 사용 예
 *
 */
public class CombineLatestExample {
    public void combineLatest() {
        String[] data1 = { "6", "7", "4", "2" };
        String[] data2 = { DIAMOND, STAR, PENTAGON };
        
        Observable<String> source = Observable.combineLatest(
                // 첫 번째 데이터
                Observable.fromArray(data1)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), 
                (shape, notUsed) -> getColor(shape)),
                // 두 번째 데이터
                Observable.fromArray(data2)
                .zipWith(Observable.interval(150L, 200L, TimeUnit.MILLISECONDS),
                        (shape, notUsed) -> getSuffix(shape)),
                // combiner
                (v1, v2) -> v1 + v2
                );
        CommonUtils.exampleStart();
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        CombineLatestExample demo = new CombineLatestExample();
        demo.combineLatest();
    }
}
