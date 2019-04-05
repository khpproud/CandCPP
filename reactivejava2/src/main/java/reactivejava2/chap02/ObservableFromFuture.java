package reactivejava2.chap02;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Observable;

/**
 * 
 * fromFuture() 함수 활용 예
 *
 */
public class ObservableFromFuture {
    public void fromFuture() {
        Future<String> future = Executors.newSingleThreadExecutor().submit(
                () -> {
                    Thread.sleep(1000);
                    return "Hello Future";
                });
        Observable<String> source = Observable.fromFuture(future);
        source.subscribe(System.out::println);
    }
    
    public static void main(String[] args) {
        ObservableFromFuture demo = new ObservableFromFuture();
        demo.fromFuture();
    }
}
