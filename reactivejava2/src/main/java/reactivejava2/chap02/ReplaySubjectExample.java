package reactivejava2.chap02;

import io.reactivex.subjects.ReplaySubject;

/**
 * 
 * ReplaySubject 클래스 사용 예
 *
 */
public class ReplaySubjectExample {
    public void subject() {
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        subject.onNext("5");
        subject.onComplete();
    }
    
    public static void main(String[] args) {
        ReplaySubjectExample demo = new ReplaySubjectExample();
        demo.subject();
    }
}
