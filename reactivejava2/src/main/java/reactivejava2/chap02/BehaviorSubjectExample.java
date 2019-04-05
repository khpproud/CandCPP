package reactivejava2.chap02;

import io.reactivex.subjects.BehaviorSubject;

/**
 * 
 * BehaviorSubject 클래스 사용 예
 *
 */
public class BehaviorSubjectExample {
    public void subject() {
        // 6을 기본값으로 가지는 subject 객체 생성
        BehaviorSubject<String> subject = BehaviorSubject.createDefault("6");
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        subject.onNext("5");
        subject.onComplete();
    }
    
    public static void main(String[] args) {
        BehaviorSubjectExample demo = new BehaviorSubjectExample();
        demo.subject();
    }
}
