package reactivejava2.chap02;

import io.reactivex.subjects.PublishSubject;

/**
 * 
 * PublishSubject 클래스 사용 예
 *
 */
public class PublishSubjectExample {
    public void subject() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println("subscriber #2 => " + data));
        subject.onNext("5");
        subject.onComplete();
    }
    
    public static void main(String[] args) {
        PublishSubjectExample demo = new PublishSubjectExample();
        demo.subject();
    }
}
