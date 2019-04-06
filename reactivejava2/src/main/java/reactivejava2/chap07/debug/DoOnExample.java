package reactivejava2.chap07.debug;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * doOnNext(), doOnComplete(), doOnError(), doOnEach(),
 * doOnSubscribe(), doOnDispose(), doOnTerminate() 등  
 * Observable 이벤트 로깅 함수 사용 예
 *
 */
public class DoOnExample {
    public void run() {
        String[] orgs = { "1", "3", "5" };
        Observable<String> source = Observable.fromArray(orgs);
        
        source.doOnNext(data -> Log.d("onNext()", data))
        .doOnComplete(() -> Log.d("onComplete()"))
        .doOnError(e -> Log.e("onError()", e.getMessage()))
        .subscribe(Log::i);
    }
    
    // doOnError()를 발생시키기 위한 예
    public void withError() {
        Integer[] divider = { 10, 5, 0 };
        
        Observable.fromArray(divider)
        .map(div -> 100 / div)
        .doOnNext(data -> Log.d("onNext()", data))
        .doOnComplete(() -> Log.d("onComplete()"))
        .doOnError(e -> Log.e("onError()", e.getMessage()))
        .subscribe(Log::i);
    }
    
    // Notification<T> 객체 전달 받아 이벤트 별로 처리
    public void doOnEach() {
        String[] data = { "ONE", "TWO", "THREE" };
        Observable<String> source = Observable.fromArray(data);
        
        source.doOnEach(noti -> {
            if (noti.isOnNext()) Log.d("onNext()", noti.getValue());
            if (noti.isOnComplete()) Log.d("onComplete()");
            if (noti.isOnError()) Log.e("onError()", noti.getError().getMessage());
        }).subscribe(Log::i);
    }
    
    // Observer 인터페이스 활용 예
    public void doOnEachObserver() {
        String[] args = { "1", "3", "5" };
        Observable<String> source = Observable.fromArray(args);
        
        source.doOnEach(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                // doOnEach() 에서는 onSubscribe() 함수가 호출 되지 않음
            }

            @Override
            public void onNext(String t) {
                Log.d("onNext()", t);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError()", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("onComplete()");
            }
        }).subscribe(Log::i);
    }

    // Observable을 구독 또는 해지 했을 때 각각을 로깅하기 위한 함수
    public void doOnSubscribeDispose() {
        String[] orgs = { "1", "3", "5", "2", "4", "6" };
        Observable<String> source = Observable.fromArray(orgs)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
                .doOnSubscribe(d -> Log.d("onSubscribe()"))
                .doOnDispose(() -> Log.d("onDispose()"));

        Disposable d = source.subscribe(Log::i);
        
        CommonUtils.sleep(200);
        d.dispose();
        CommonUtils.sleep(300);
    }
    
    // doOnSubscribe() 와 doOnDispose() 함수를 한꺼번에 처리
    public void doOnLifecycle() {
        String[] orgs = { "1", "3", "5", "2", "6" };
        Observable<String> source = Observable.fromArray(orgs)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
                .doOnLifecycle(d -> Log.d("onSubscribe()"), () -> Log.d("onDispose()"));
        Disposable d = source.subscribe(Log::i);
        
        CommonUtils.sleep(200);
        d.dispose();
        CommonUtils.sleep(300);
    }
    
    // doOnTerminate() 함수 사용 예
    public void doOnTerminate() {
        String[] orgs = { "1", "3", "5" };
        Observable<String> source = Observable.fromArray(orgs);
        
        source.doOnTerminate(() -> Log.d("onTerminate()"))
        .doOnComplete(() -> Log.d("onComplete()"))
        .subscribe(Log::i);
    }
    
    public static void main(String[] args) {
        DoOnExample demo = new DoOnExample();
        demo.run();
        demo.doOnEach();
        demo.doOnEachObserver();
        demo.doOnSubscribeDispose();
        demo.doOnLifecycle();
        demo.doOnTerminate();
        //demo.withError();
    }
}
