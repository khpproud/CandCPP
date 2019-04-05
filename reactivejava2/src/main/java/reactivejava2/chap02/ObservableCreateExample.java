package reactivejava2.chap02;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.functions.Consumer;

/**
 * 
 * @author patrick
 * create() 함수 활용 예
 */
public class ObservableCreateExample {
    public void emit() {
        Observable<Integer> source = Observable.create(
                (ObservableEmitter<Integer> emitter) -> {
                    emitter.onNext(100);
                    emitter.onNext(200);
                    emitter.onNext(300);
                    emitter.onComplete();
                });
        source.subscribe(System.out::println);
    }
    
    //익명 객체로 표현 시
    public void subscribeAnonymously() {
        Observable<Integer> source = Observable.create(
                (ObservableEmitter<Integer> emitter) -> {
                    emitter.onNext(100);
                    emitter.onNext(200);
                    emitter.onNext(300);
                    emitter.onComplete();
                });
        source.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer t) throws Exception {
                System.out.println("Result : " + t);
                
            }
        });
    }
    
    public static void main(String[] args) {
        ObservableCreateExample demo = new ObservableCreateExample();
        demo.emit();
        demo.subscribeAnonymously();
    }
}
