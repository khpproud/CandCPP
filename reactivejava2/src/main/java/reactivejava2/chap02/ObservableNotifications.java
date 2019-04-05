package reactivejava2.chap02;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 
 * isDisposed() 함수 활용 예
 */
public class ObservableNotifications {
    public void emit() {
        Observable<String> source = Observable.just("RED", "GREEN", "YELLOW");
        
        Disposable d = source.subscribe(
                v -> System.out.println("onNext() : value : " + v),
                error -> System.err.println("onError() : error : " + error.getMessage()),
                () -> System.out.println("onComplete()")
        );
        
        System.out.println("isDisposed() : " + d.isDisposed());
    }
    
    public static void main(String[] args) {
        new ObservableNotifications().emit();
    }
}
