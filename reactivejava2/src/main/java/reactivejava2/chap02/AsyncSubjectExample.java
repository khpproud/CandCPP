package reactivejava2.chap02;

import io.reactivex.Observable;
import io.reactivex.subjects.AsyncSubject;

/**
 * 
 * AsyncSubject 클래스 사용 예
 *
 */
public class AsyncSubjectExample {
    public void subject() {
        AsyncSubject<String> subject = AsyncSubject.create();
        // 구독자가 subscribe를 호출
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        // 데이터를 발행
        subject.onNext("1");
        subject.onNext("2");
        subject.subscribe(data -> System.out.println("subscriber #2 => " + data));
        subject.onNext("3");
        // onComplete()가 호출된 후 마지막으로 입력된 데이터가 구독자에게 최종 전달
        subject.onComplete();
    }
    
    // 구독자로 동작하는 AsyncSubject
    public void asSubscriber() {
        Float[] temparature = { 10.1f, 12.4f, 15.9f };
        Observable<Float> source = Observable.fromArray(temparature);
        
        AsyncSubject<Float> subject = AsyncSubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        source.subscribe(subject);
    }
    
    // onComplete() 함수를 호출한 후 구독
    public void afterComplete() {
        AsyncSubject<Integer> subject = AsyncSubject.create();
        subject.onNext(10);
        subject.onNext(11);
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        subject.onNext(12);
        subject.onComplete();
        // onComplete 호출 후에 onNext는 무시됨
        subject.onNext(13);
        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        subject.subscribe(data -> System.out.println("Subscriber #3 => " + data));
    }
    
    public static void main(String[] args) {
        AsyncSubjectExample demo = new AsyncSubjectExample();
        demo.subject();
        demo.asSubscriber();
        demo.afterComplete();
    }
}
