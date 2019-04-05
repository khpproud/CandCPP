package reactivejava2.chap04.combine;

import java.util.Scanner;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;
import reactivejava2.common.Log;

/**
 * 
 * 리액티브 연산자로 합계 구하기 예제
 *
 */
public class ReactiveSum {
    public static void main(String[] args) {
        new ReactiveSum().run();
    }
    
    public void run() {
        ConnectableObservable<String> source = userInput();
        Observable<Integer> a = source
                .filter(str -> str.startsWith("a:"))
                .map(str -> str.replace("a:", ""))
                .map(Integer::parseInt);
        Observable<Integer> b = source
                .filter(str -> str.startsWith("b"))
                .map(str -> str.replace("b:", ""))
                .map(Integer::parseInt);
        Observable.combineLatest(
                a,
                b,
                (x, y) -> x + y)
        .subscribe(res -> Log.i("Result : " + res));
        source.connect();
    }
    
    // 사용자 입력을 받기 위한 함수 정의
    public ConnectableObservable<String> userInput() {
        return Observable.create((ObservableEmitter<String> emitter) -> {
            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.print("Input : ");
                String line = in.nextLine();
                emitter.onNext(line);
                
                if (line.indexOf("exit") >= 0) {
                    in.close();
                    break;
                }
            }
        }).publish();
    }
}
