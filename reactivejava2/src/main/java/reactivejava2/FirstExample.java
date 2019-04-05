package reactivejava2;

import io.reactivex.Observable;

public class FirstExample {
    public void emit() {
        Observable.just("Hello", "RxJava2!!!")
        .subscribe(System.out::println);
        // data -> System.out.println(data); 와 동일
        
        Observable.just(1, 2, 3, 4, 5, 6)
        .subscribe(System.out::println);
    }
    
    public static void main(String[] args) {
        FirstExample demo = new FirstExample();
        demo.emit();
    }
}
