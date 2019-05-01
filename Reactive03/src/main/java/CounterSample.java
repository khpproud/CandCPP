import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * 두 스레드에서 같은 객체의 변경 작업을 수행하는 예제
 * 스레드 안정적이지 않은 공유 가변 객체를 다룸
 */
public class CounterSample {
    public static void main(String[] args) throws InterruptedException {
        // 숫자를 세는 객체
        final Counter counter = new Counter();

        // onComplete Action 정의
        Action onComplete = () -> System.out.println("counter.get() = " + counter.get());

        // Counter의 increment 메서드를 10,000번 호출
        Flowable.range(1, 10_000)
                // Flowable을 다른 스레드에서 처리
                .subscribeOn(Schedulers.computation())
                // 다른 스레드에서 처리
                .observeOn(Schedulers.computation())
                // 구독
                .subscribe(
                        // 데이터를 받을 때의 처리 onNext()
                        data -> counter.increment(),
                        // 에러 발생시 처리 onError()
                        e -> e.printStackTrace(),
                        // 완료 시 처리 onComplete()
                        onComplete);

        // 다른 스레드에서 동시에 처리
        Flowable.range(1, 10_000)
                // Flowable을 다른 스레드에서 처리
                .subscribeOn(Schedulers.computation())
                // 다른 스레드에서 처리
                .observeOn(Schedulers.computation())
                // 구독
                .subscribe(
                        // 데이터를 받을 때의 처리 onNext()
                        data -> counter.increment(),
                        // 에러 발생시 처리 onError()
                        e -> e.printStackTrace(),
                        // 완료 시 처리 onComplete()
                        onComplete);

        // 잠시 기다림
        Thread.sleep(1000L);
    }
}

class Counter {
    private volatile int count;

    void increment() {
        count++;
    }

    int get() {
        return count;
    }
}
