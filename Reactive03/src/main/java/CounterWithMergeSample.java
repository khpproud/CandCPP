import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * merge 메서드로 결합하는 예
 */
public class CounterWithMergeSample {
    public static void main(String[] args) throws InterruptedException {
        // 순차적으로 값을 증가시키는 Counter 객체
        final Counter counter = new Counter();

        // Counter의 increment 메서드를 10,000번 호출
        Flowable<Integer> source1 = Flowable.range(1, 10_000)
                // Flowable이 다른 스레드 처리 작업을 하도록
                .subscribeOn(Schedulers.computation())
                // 구독이 다른 스레드 에서 처리되도록
                .observeOn(Schedulers.computation());

        // 다른 스레드에서 동시에 실행
        Flowable<Integer> source2 = Flowable.range(1, 10_000)
                // Flowable이 다른 스레드 처리 작업을 하도록
                .subscribeOn(Schedulers.computation())
                // 구독이 다른 스레드 에서 처리되도록
                .observeOn(Schedulers.computation());

        // 두 Flowable을 합침
        Flowable.merge(source1, source2)
                // 구독
                .subscribe(
                        // onNext
                        data -> counter.increment(),
                        // onError
                        e -> e.printStackTrace(),
                        // onComplete
                        () -> System.out.println("counter.get() = " + counter.get())
                );

        // 잠시 기다림
        Thread.sleep(1000L);
    }
}
