package reactivejava2.chap03;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import reactivejava2.common.Log;

/**
 * 
 * flatMap() 함수 사용 예
 *
 */
public class FlatMapExample {
    String[] balls = { "1", "3", "5" };
    
    public void flatMapFunction() {
        // 함수를 별도로 정의
        Function<String, Observable<String>> getDoubleDiamons = 
                ball -> Observable.just(ball + "<>", ball + "<>");
        
        Observable<String> source = Observable.fromArray(balls)
                .flatMap(getDoubleDiamons);
        source.subscribe(Log::i);
    }
    
    // flatMap() 함수의 인라인 람다 표현식
    public void flatMap() {
        Observable<String> source = Observable.fromArray(balls)
                .flatMap(ball -> Observable.just(ball + "<>", ball + "<>"));
        source.subscribe(Log::i);
    }
    
    public static void main(String[] args) {
        FlatMapExample demo = new FlatMapExample();
        demo.flatMapFunction();
        demo.flatMap();
    }
}
