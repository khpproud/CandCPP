package reactivejava2.chap05;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;
import reactivejava2.common.OkHttpHelper;

/**
 * 
 * RxJava의 스케줄러를 활용한 URL 호출
 *
 */
public class CallbackHeaven {
    public void usingContent() {
        CommonUtils.exampleStart();
        
        Observable<String> source = Observable.just(OkHttpHelper.GITHUB_ZEN_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get)
                .concatWith(Observable.just(OkHttpHelper.GITHUB_ZEN_URL)
                        .map(OkHttpHelper::get));
        source.subscribe(Log::it);
        CommonUtils.sleep(5000);
    }
    
    // zip()을 활용한 동시 요청 후 결과만 취합
    public void usingZip() {
        CommonUtils.exampleStart();
        
        Observable<String> source1 = Observable.just(OkHttpHelper.GITHUB_ZEN_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get);
        Observable<String> source2 = Observable.just(OkHttpHelper.GITHUB_ZEN_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get);
        
        Observable.zip(source1, source2, (a, b) -> ("\n>>" + a + "\n>>" + b))
            .subscribe(Log::it);
        CommonUtils.sleep(5000);
    }
    
    public static void main(String[] args) {
        CallbackHeaven demo = new CallbackHeaven();
        demo.usingContent();
        demo.usingZip();
    }
}
