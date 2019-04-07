package reactivejava2.chap08.flowable;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * 배압(Back pressure) 상황 처리 예 
 *
 */
public class BackpressureExample {
    // 배압 상황 연출
    public void makeBackpressure() {
        CommonUtils.exampleStart();
        
        // PublishSubject는 뜨거운 Observable
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.observeOn(Schedulers.computation())
        .subscribe(data -> {
            // 100ms 후 데이터를 처리
            CommonUtils.sleep(100);
            Log.it(data);
        }, err -> Log.et(err.toString()));
        
        // 뜨거운 Observable로 50,000,000 개의 데이터를 연속으로 발행함
        for (int i = 0; i < 50_000_000; ++i) {
            subject.onNext(i);
        }
        subject.onComplete();
    }
    
    // onBackpressureBuffer() 함수의 활용 예
    public void usingBuffer() {
        CommonUtils.exampleStart();
        
        Flowable.range(1, 50_000_000)
        .onBackpressureBuffer(128, () -> {}, BackpressureOverflowStrategy.DROP_OLDEST)
        .observeOn(Schedulers.computation())
        .subscribe(data -> {
            // 100ms 후 데이터 처리
            CommonUtils.sleep(100);
            Log.it(data);
        }, err -> Log.e(err.getMessage()));
    }
    
    // onBackpressureDrop() 함수의 활용 예
    public void usingDrop() {
        CommonUtils.exampleStart();
        
        Flowable.range(1, 50_000_000)
        .onBackpressureDrop()
        .observeOn(Schedulers.computation())
        .subscribe(data -> {
            CommonUtils.sleep(100);
            Log.it(data);
        }, err -> Log.e(err.getMessage()));
        // 계산 스케줄러에서 데이터를 다운스트림으로 발행할 수 있도록 충분한 시간 기다려줌
        CommonUtils.sleep(20_000);
    }
    
    // onBackpressureLatest() 함수 활용 예
    public void usingLatest() {
        CommonUtils.exampleStart();
        
        Flowable.range(1, 50_000_000)
        .onBackpressureLatest()
        .observeOn(Schedulers.computation())
        .subscribe(data -> {
            CommonUtils.sleep(100);
            Log.it(data);
        }, err -> Log.e(err.getMessage()));
        // 계산 스케줄러에서 데이터를 다운스트림으로 발행할 수 있도록 충분한 시간 기다려줌
        CommonUtils.sleep(20_000);
    }
    
    public static void main(String[] args) {
        BackpressureExample demo = new BackpressureExample();
        //demo.makeBackpressure();
        //demo.usingBuffer();
        //demo.usingDrop();
        // 마지막 데이터를 다운스트림으로 발행
        demo.usingLatest();
    }
}
