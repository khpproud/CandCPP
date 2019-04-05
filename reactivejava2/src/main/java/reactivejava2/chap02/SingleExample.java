package reactivejava2.chap02;

import io.reactivex.Observable;
import io.reactivex.Single;
import reactivejava2.common.Order;

/**
 * 
 * Single 클래스 간단한 사용 예
 *
 */
public class SingleExample {
    public void just() {
        Single<String> source = Single.just("Hello single!!!");
        source.subscribe(System.out::println);
    }
    
    // Observable 클래스에서 Single 클래스 활용 예
    public void fromObservable() {
        // 1. 기존 Observable에서 Single 객체로 변환
        Observable<String> source = Observable.just("Hello single!!");
        Single.fromObservable(source)
        .subscribe(System.out::println);
        
        // 2. single() 함수를 호출해 Single 객체 생성
        Observable.just("Hello Single!!!!")
        .single("default item")
        .subscribe(System.out::println);
        
        // 3. first() 함수를 호출해 Single 객체 생성
        String[] colors = { "RED", "GREEN", "BLUE" };
        Observable.fromArray(colors)
        .first("default value")
        .subscribe(System.out::println);
        
        // 4. empty Observable에서 Single 객체 생성
        Observable.empty()
        .single("default value")
        .subscribe(System.out::println);
        
        // 5. take() 함수에서 Single 객체 생성
        Observable.just(new Order("ORD-1"), new Order("ORD-2"))
        .take(1)
        .single(new Order("Default Order"))
        .subscribe(System.out::println);
    }
    
    // just() 함수에 여러 개 값 넣기 
    public void errorCase() {
        Single<String> source = Observable.just("Hello Single", "Error").single("Default item");
        source.subscribe(System.out::println);
    }
    
    public static void main(String[] args) {
        SingleExample demo = new SingleExample();
        demo.just();
        demo.fromObservable();
        demo.errorCase();
    }
}
