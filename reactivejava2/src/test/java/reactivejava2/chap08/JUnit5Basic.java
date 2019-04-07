package reactivejava2.chap08;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.reactivex.Observable;
import reactivejava2.common.Log;
import reactivejava2.common.Shape;

/**
 * 
 * JUnit5 기본 테스트 코드
 *
 */
class JUnit5Basic {
    @DisplayName("JUnit 5 First Example")
    @Test
    void testFirst() {
        int expected = 4;
        int actual = 1 + 3;
        assertEquals(expected, actual);
    }
    
    @DisplayName("test getShape() Observable")
    @Test
    void testGetShapeObservable() {
        String[] data = { "1", "2-R", "3-T" };
        Observable<String> source = Observable.fromArray(data)
                .map(Shape::getShape);
        
        String[] expected = { Shape.BALL, Shape.RECTANGLE, Shape.TRIANGLE };
        List<String> actual = new ArrayList<>();
        source.doOnNext(Log::d)
        .subscribe(actual::add);
        
        assertEquals(Arrays.asList(expected), actual);
    }
}
