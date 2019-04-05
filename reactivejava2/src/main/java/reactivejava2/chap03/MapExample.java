package reactivejava2.chap03;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import reactivejava2.common.Log;

/**
 * 
 * map() 함수 사용 예
 *
 */
public class MapExample {
    public void map() {
        String[] balls = { "1", "2", "3", "5" };
        Observable<String> source = Observable.fromArray(balls)
                .map(ball -> ball + "<>");
        source.subscribe(Log::i);
    }
    
    // Function 인터페이스를 적용한 map() 함수
    public void mapFunction() {
        Function<String, String> getDiamond = ball -> ball + "<>";
        
        String[] balls = { "1", "2", "3", "5" };
        Observable<String> source = Observable.fromArray(balls)
                .map(getDiamond);
        source.subscribe(Log::i);
    }
    
    // 데이터 타입 추론
    public void mappingType() {
        Function<String, Integer> ballToIndex = ball -> {
            switch (ball) {
            case "RED" : return 1;
            case "YELLOW" : return 2;
            case "GREEN" : return 3;
            case "BLUE" : return 4;
            default : return -1;
            }
        };
        
        String[] balls = { "RED", "YELLOW", "GREEN", "BLUE" };
        Observable<Integer> source = Observable.fromArray(balls)
                .map(ballToIndex);
        source.subscribe(Log::i);
    }
    
    public static void main(String[] args) {
        MapExample demo = new MapExample();
        demo.map();
        demo.mapFunction();
        demo.mappingType();
    }
}
