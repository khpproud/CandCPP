package reactivejava2.chap08;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import reactivejava2.common.Shape;

/**
 * 
 * TestObserver를 활용한 테스트
 *
 */
class TestObserverExample {
    @DisplayName("#1 : using TestObserver for Shape.getShape()")
    @Test
    void testGetShapeUsingObserver() {
        String[] data = { "1", "2-R", "3-T" };
        Observable<String> source = Observable.fromArray(data)
                .map(Shape::getShape);
        String[] expected = { Shape.BALL, Shape.RECTANGLE, Shape.TRIANGLE };
        source.test().assertResult(expected);
    }
    
    @DisplayName("assertFailure() example")
    @Test
    void assertFailureExample() {
        String[] data = { "100", "200", "$300" };
        Observable<Integer> source = Observable.fromArray(data)
                .map(Integer::parseInt);
        
        source.test().assertFailure(NumberFormatException.class, 100, 200);
    }

    @DisplayName("assertFailureAndMessage() example")
    @Test
    void assertFailureAndMessage() {
        String[] data = { "100", "200", "#300" };
        Observable<Integer> source = Observable.fromArray(data)
                .map(Integer::parseInt);
        source.test().assertFailureAndMessage(NumberFormatException.class, 
                "For input string: \"#300\"", 100, 200);
    }
    
    @DisplayName("assertComplete() example")
    @Test
    void assertComplete() {
        Observable<String> source = Observable.create(
                (ObservableEmitter<String> e) -> {
                    e.onNext("Hello RxJava");
                    e.onComplete();
                });
        source.test().assertComplete();
    }
}
