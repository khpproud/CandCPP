package reactivejava2.chap03;

import io.reactivex.Observable;
import io.reactivex.Single;
import reactivejava2.common.Log;

/**
 * 
 * filter() 류 함수 사용 예
 *
 */
public class FilterExample {
    public void filter() {
        String[] objs = {"1 Circle", "2 Diamond", "3 Triangle", "4 Diamond", "5 Circle" };
        Observable<String> source = Observable.fromArray(objs)
                .filter(obj -> obj.endsWith("Circle"));
        source.subscribe(Log::i);
    }
    
    // filter() 함수를 이용한 짝수 필터링
    public void evenNumbers() {
        Integer[] data = { 100, 23, 46, 31, 10 };
        Observable<Integer> source = Observable.fromArray(data)
                .filter(i -> i % 2 == 0);
        source.subscribe(Log::i);
    }
    
    // filter() 함수 류의 활용 예
    public void otherFilters() {
        Integer[] numbers = { 100, 200, 300, 400, 500 };
        Single<Integer> single;
        Observable<Integer> source;
        
        // 1.first
        single = Observable.fromArray(numbers).first(-1);
        single.subscribe(data -> Log.i("fist() : " + data));
        
        // 2.last
        single = Observable.fromArray(numbers).last(999);
        single.subscribe(data -> Log.i("last() : " + data));
        
        // 3.take
        source = Observable.fromArray(numbers).take(3);
        source.subscribe(data -> Log.i("take(3) : " + data));
        
        // 4.takeLast
        source = Observable.fromArray(numbers).takeLast(3);
        source.subscribe(data -> Log.i("takeLast(3) : " + data));
        
        // 5.skip
        source = Observable.fromArray(numbers).skip(3);
        source.subscribe(data -> Log.i("skip(3) : " + data));
        
        // 6.skipLast
        source = Observable.fromArray(numbers).skipLast(3);
        source.subscribe(data -> Log.i("skipLast(3) : " + data));
    }
    
    public static void main(String[] args) {
        FilterExample demo = new FilterExample();
        demo.filter();
        demo.evenNumbers();
        demo.otherFilters();
    }
}
