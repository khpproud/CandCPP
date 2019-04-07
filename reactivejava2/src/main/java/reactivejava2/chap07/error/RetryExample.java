package reactivejava2.chap07.error;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;
import reactivejava2.common.OkHttpHelper;

/**
 * 
 * 예외 발생 시 재시도의 예
 *
 */
public class RetryExample {
    private static final String URL = "https://api.github.com/zen";
    
    // 5번 재시도
    public void retry5() {
        // 시간 표시용
        CommonUtils.exampleStart();
        
        Observable<String> source = Observable.just(URL)
                .map(OkHttpHelper::getT)
                .retry(5)
                .onErrorReturn(e -> CommonUtils.ERROR_CODE);
        source.subscribe(data -> Log.it("result : " + data));
        CommonUtils.exampleComplete();
    }
    
    // 딜레이를 설정 후 5번 재시도
    public void retryWithDelay() {
        final int RETRY_MAX = 5;
        final int RETRY_DELAY = 1000;
        
        CommonUtils.exampleStart();
        Observable<String> source = Observable.just(URL)
                .map(OkHttpHelper::getT)
                .retry((retryCnt, e) -> {
                    Log.e("retryCnt = " + retryCnt);
                    CommonUtils.sleep(RETRY_DELAY);
                    return retryCnt < RETRY_MAX ? true : false;
                })
                .onErrorReturn(e -> CommonUtils.ERROR_CODE);
        source.subscribe(data -> Log.it("result : " + data));
    }
    
    // retryUntil() 함수 사용 예
    public void retryUntil() {
        CommonUtils.exampleStart();
        
        Observable<String> source = Observable.just(URL)
                .map(OkHttpHelper::getT)
                .subscribeOn(Schedulers.io())
                .retryUntil(() -> {
                    if (CommonUtils.isNetworkAvailable())
                        return true;
                    CommonUtils.sleep(1000);
                    return false;
                });
        source.subscribe(Log::it);
        
        // IO 스케줄러에서 실행되기 때문에 sleep() 함수가 필요
        CommonUtils.sleep(6000);
    }
    
    // retryWhen() 함수 사용 예
    public void retryWhen() {
        Observable.create((ObservableEmitter<String> e) -> {
            Log.d("subscribing");
            e.onError(new RuntimeException("always fails"));
        }).retryWhen(attempts -> {
            return attempts.zipWith(Observable.range(1, 3), (n, i) -> i)
                    .flatMap(i -> {
                        Log.d("delay retry by " + i + " seconds");
                        return Observable.timer(i, TimeUnit.SECONDS);
                    });
        }).blockingForEach(Log::d);
    }
    
    public static void main(String[] args) {
        RetryExample demo = new RetryExample();
        //demo.retry5();
        //demo.retryWithDelay();
        //demo.retryUntil();
        demo.retryWhen();
    }
}
