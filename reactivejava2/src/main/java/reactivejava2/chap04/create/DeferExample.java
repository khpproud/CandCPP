package reactivejava2.chap04.create;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;
import reactivejava2.common.Shape;

/**
 * 
 * defer() 함수 사용 예
 *
 */
public class DeferExample {
    Iterator<String> colors = Arrays.asList("1", "3", "5", "6").iterator();
    
    public void defer() {    
        Callable<Observable<String>> supplier = () -> getObservable();
        Observable<String> source = Observable.defer(supplier);
        
        source.subscribe(val -> Log.i("Subscriber #1 : " + val));
        source.subscribe(val -> Log.i("Subscriber #2 : " + val));
        CommonUtils.exampleComplete();
    }
    
    // defer() 사용하지 않고 getObservable()에서 반환하는 Observable 구독
    public void notDefered() {
        Observable<String> source = getObservable();
        source.subscribe(val -> Log.i("Subscriber #1 : " + val));
        source.subscribe(val -> Log.i("Subscriber #2 : " + val));
        CommonUtils.exampleComplete();
    }
    
    // 번호가 적힌 도형을 발행하는 Observable을 생성
    private Observable<String> getObservable() {
        if (colors.hasNext()) {
            String color = colors.next();
            return Observable.just(
                    Shape.getString(color, Shape.BALL),
                    Shape.getString(color, Shape.RECTANGLE),
                    Shape.getString(color, Shape.PENTAGON));
        }
        return Observable.empty();
    }
    
    public static void main(String[] args) {
        DeferExample demo = new DeferExample();
        demo.defer();
        demo.defer();
        //demo.notDefered();
    }
}
