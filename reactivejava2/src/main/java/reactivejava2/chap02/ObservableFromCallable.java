package reactivejava2.chap02;

import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * 
 * fromCallable() 함수 활용 예
 *
 */
public class ObservableFromCallable {
    public void fromCallable() {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            return "Hello Callable!!!";
        };
        
        Observable<String> source = Observable.fromCallable(callable);
        source.subscribe(System.out::println);
    }
    
    // 람다 표현식을 사용하지 않을 때
    public void fromCallableNotLambda() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Hello Callable";
            }
        };
        Observable<String> source = Observable.fromCallable(callable);
        source.subscribe(System.out::println);
    }
    
    public static void main(String[] args) {
        ObservableFromCallable demo = new ObservableFromCallable();
        demo.fromCallable();
        demo.fromCallableNotLambda();
    }
}
