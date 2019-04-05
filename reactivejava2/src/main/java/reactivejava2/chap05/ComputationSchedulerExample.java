package reactivejava2.chap05;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * 계산 스케줄러 사용 예
 *
 */
public class ComputationSchedulerExample {
    public void compute() {
        String[] orgs = { "1", "3", "5" };
        Observable<String> source = Observable.fromArray(orgs)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        
        // 구독 #1
        source.map(item -> "<<" + item + ">>")
        // interval() 함수는 내부적으로 계산 스케줄러를 사용하기 때문에 없어도 됨
        .subscribeOn(Schedulers.computation())
        .subscribe(Log::i);
        
        // 구독 #2
        source.map(item -> "##" + item + "##")
        .subscribe(Log::i);
        
        CommonUtils.sleep(500);
    }
    
    public static void main(String[] args) {
        ComputationSchedulerExample demo = new ComputationSchedulerExample();
        demo.compute();
    }
}
