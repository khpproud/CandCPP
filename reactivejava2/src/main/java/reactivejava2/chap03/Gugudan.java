package reactivejava2.chap03;

import java.util.Scanner;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import reactivejava2.common.Log;

/**
 * 
 * 구구단 만들기 (RxJava 활용)
 *
 */
public class Gugudan {
    static Scanner in = new Scanner(System.in);
    
    // plain java
    public static void gugudan(int dan) {
        for (int i = 1; i <= 9; i++) {
            Log.i(dan + " * " + i + " = " + (dan * i));
        }
    }
    
    // step 1. for문의 Observable 변환
    public static void reactive1(int dan) {
        Observable<Integer> source = Observable.range(1, 9);
        source.subscribe(i -> System.out.println(dan + " * " + i + " = " + dan * i));
    }
    
    // step 2. 사용자 함수 정의
    public static void reactive2(int dan) {
        Function<Integer, Observable<String>> gugudan = num -> 
            Observable.range(1, 9).map(i -> num + " * " + i + " = " + num * i);
        Observable<String> source = Observable.just(dan).flatMap(gugudan);
        source.subscribe(Log::i);
    }
    
    // step 3. flatMap() 함수를 좀 더 활용
    public static void reactive3(int dan) {
        Observable<String> source = Observable.just(dan)
                .flatMap(gugu -> Observable.range(1, 9), 
                        (gugu, i) -> gugu + " * " + i + " = " + gugu * i);
        source.subscribe(Log::i);
    }
    
    public static void main(String[] args) {
        System.out.print("Gugudan Input : ");
        int dan = Integer.parseInt(in.nextLine());
        //gugudan(dan);
        //reactive1(dan);
        //reactive2(dan);
        reactive3(dan);
        in.close();
    }
}
