package reactivejava2.chap07.error;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * 예외 처리 예
 *
 */
public class ExceptionHandling {
    // 일반적인 Java의 try-catch 예외 처리 구문
    public void cannotCatch() {
        Observable<String> source = Observable.create(
                (ObservableEmitter<String> emitter) -> {
                   emitter.onNext("1");
                   emitter.onError(new Exception("Some error"));
                   emitter.onNext("3");
                   emitter.onComplete();
                });
                
        try {
            source.subscribe(Log::i);
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }
    
    // RxJava의 onErrorREturn() 함수로 예외 캐치
    public void onErrorReturn() {
        String[] grades = { "70", "88", "$100", "93", "82" };
        
        Observable<Integer> source = Observable.fromArray(grades)
                .map(Integer::parseInt)
                .onErrorReturn(e -> {
                    if (e instanceof NumberFormatException)
                        e.printStackTrace();
                    // 음수 리턴
                    return -1;
                });
        source.subscribe(data -> {
           if (data < 0) {
               Log.e("Wrong Data found");
               return;
           } 
           Log.i("Grade is " + data);
        });
    }
    
    // 구독할 때 onError() 함수에서 예외 처리 예
    public void onError() {
        String[] grades = { "70", "88", "$100", "65" };
        
        Observable<Integer> source = Observable.fromArray(grades)
                .map(Integer::parseInt);
        source.subscribe(data -> Log.i("Grade is " + data), 
                e -> {
                    if (e instanceof NumberFormatException)
                        e.printStackTrace();
                    Log.e("Wrong Data found");
                });
    }
    
    // Throwable 객체를 전달하지 않는 onErrorReturnItem() 함수 예
    public void onErrorReturnItem() {
        String[] grades = { "50", "48", "$100", "78" };
        
        Observable<Integer> source = Observable.fromArray(grades)
                .map(Integer::parseInt)
                .onErrorReturnItem(-1);
        
        source.subscribe(data -> {
            if (data < 0) {
                Log.e("Wrong Data found");
                return;
            }
            Log.i("Grade is " + data);
        });
    }
    
    // onErrorResumeNext() 에러가 발생했을 때 내가 원하는 Observable로 대체
    public void onErrorResumeNext() {
        String[] salesData = { "100", "200", "A300" };
        Observable<Integer> onParseError = Observable.defer(() -> {
            Log.d("send email to administrator");
            return Observable.just(-1);
        }).subscribeOn(Schedulers.io());
        
        Observable<Integer> source = Observable.fromArray(salesData)
                .map(Integer::parseInt)
                .onErrorResumeNext(onParseError);
        source.subscribe(data -> {
            if (data < 0) {
                Log.e("Wrond Data found");
                return;
            }
            Log.i("Sales data is " + data);
        });
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        ExceptionHandling demo = new ExceptionHandling();
        //demo.cannotCatch();
        //demo.onErrorReturn();
        //demo.onError();
        //demo.onErrorReturnItem();
        demo.onErrorResumeNext();
    }
}
