package reactivejava2.chap04.combine;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;
import reactivejava2.common.Shape;

/**
 * 
 * zip() 함수 사용 예
 *
 */
public class ZipExample {
    public void zip() {
        String[] shapes = { Shape.BALL, Shape.PENTAGON, Shape.STAR };
        String[] coloredTriangles = { "2-T", "6-T", "4-T" };
        
        Observable<String> source = Observable.zip(
                Observable.fromArray(shapes).map(Shape::getSuffix),
                Observable.fromArray(coloredTriangles).map(Shape::getColor),
                (suffix, color) -> color + suffix);
        source.subscribe(Log::i);
    }
    
    // 숫자 결합
    public void zipNumbers() {
        Observable<Integer> source = Observable.zip(
                Observable.just(100, 200, 300),
                Observable.just(10, 20, 30),
                Observable.just(1, 2, 3),
                (a, b, c) -> a + b + c);
        
        source.subscribe(Log::i);
    }
    
    // zipWith() 이용한 결합
    public void zipWith() {
        Observable<Integer> source = Observable.zip(
                Observable.just(100, 200, 300),
                Observable.just(10, 20, 30),
                (a, b) -> a + b)
                .zipWith(Observable.just(1, 2, 3), (ab, c) -> ab + c);
        source.subscribe(Log::i);
    }
    
    // interval() 함수를 이용한 시간 결합
    public void zipInterval() {
        Observable<String> source = Observable.zip(
                Observable.just("RED", "GREEN", "BLUE"),
                Observable.interval(200L, TimeUnit.MILLISECONDS),
                (value, i) -> value);
        CommonUtils.exampleStart();
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        ZipExample demo = new ZipExample();
        demo.zip();
        demo.zipNumbers();
        demo.zipWith();
        demo.zipInterval();
    }
}
