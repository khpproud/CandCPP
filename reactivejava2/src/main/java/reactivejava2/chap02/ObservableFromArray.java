package reactivejava2.chap02;

import java.util.stream.IntStream;

import io.reactivex.Observable;

/**
 * 
 * fromArray() 함수 활용 예
 *
 */
public class ObservableFromArray {
    public void integerArray() {
        Integer[] arr = {100, 200, 300};
        Observable<Integer> source = Observable.fromArray(arr);
        source.subscribe(System.out::println);
    }
    
    public void intArray() {    
        int[] intArray = { 400, 500, 600};
        Observable<Integer> source = Observable.fromArray(toIntegerArray(intArray));
        source.subscribe(System.out::println);
    }

    // int 배열을 Integer 배열로 변환
    private static Integer[] toIntegerArray(int[] intArray) {
        return IntStream.of(intArray).boxed().toArray(Integer[]::new);
    }
    
    public static void main(String[] args) {
        ObservableFromArray demo = new ObservableFromArray();
        demo.integerArray();
        demo.intArray();
    }
}
