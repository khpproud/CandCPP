package reactivejava2.chap02;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import io.reactivex.Observable;

/**
 * 
 * fromPublisher() 함수 활용 예
 *
 */
public class ObservableFromPublisher {
    public void fromPublisher() {
        Publisher<String> publisher = (Subscriber<? super String> s) -> {
            s.onNext("Hello Observable.fromPublisher()");
            s.onComplete();
        };
        
        Observable<String> source = Observable.fromPublisher(publisher);
        source.subscribe(System.out::println);
    }
    
    // 람다 표현식을 사용하지 않는 함수
    public void fromPublisherNotLambda() {
        Publisher<String> publisher = new Publisher<String>() {
            @Override
            public void subscribe(Subscriber<? super String> s) {
                s.onNext("Hello Observable.fromPublisher()");
                s.onComplete();
            }
        };
        
        Observable<String> source = Observable.fromPublisher(publisher);
        source.subscribe(System.out::println);
    }
    
    public static void main(String[] args) {
        ObservableFromPublisher demo = new ObservableFromPublisher();
        demo.fromPublisher();
        demo.fromPublisherNotLambda();
    }
}
