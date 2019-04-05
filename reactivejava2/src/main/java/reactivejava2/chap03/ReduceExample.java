package reactivejava2.chap03;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import reactivejava2.common.Log;

/**
 * 
 * reduce() 함수 사용 예
 *
 */
public class ReduceExample {
    public void reduce() {
        String[] balls = { "1", "3", "5" };
        Maybe<String> source = Observable.fromArray(balls)
                .reduce((ball1, ball2) -> ball2 +"(" + ball1 + ")");
        source.subscribe(Log::i);
    }
    
    // Bifunction으로 람다표현식 분리
    public void biFunction() {
        String[] balls = { "1", "3", "5" };
        BiFunction<String, String, String> mergeBalls = (ball1, ball2) -> 
        ball2 + "(" + ball1 + ")";
        Maybe<String> source = Observable.fromArray(balls)
                .reduce(mergeBalls);
        source.subscribe(Log::i);
    }
    
    public static void main(String[] args) {
        ReduceExample demo = new ReduceExample();
        demo.reduce();
        demo.biFunction();
    }
}
