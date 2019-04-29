import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

// CompositeDisposableSample - 복수 개의 Disposable을 모아 dispose()를 호출해 구독을 해지 할 수 있음
public class CompositeDisposableSample {
    public static void main(String[] args) throws InterruptedException {
        // Disposable을 합친다
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(Flowable.range(1, 3)
            .doOnCancel(() -> System.out.println("No.1 canceled!"))
            .observeOn(Schedulers.computation())
            .subscribe(data -> {
                Thread.sleep(100L);
                System.out.println("No.1 : " + data);
            }));

        compositeDisposable.add(Flowable.range(4, 3)
            .doOnCancel(() -> System.out.println("No.2 canceled!"))
            .observeOn(Schedulers.computation())
            .subscribe(data -> {
                Thread.sleep(200L);
                System.out.println("No.2 : " + data);
            }));

        // 잠시 기다림
        Thread.sleep(250L);

        // 한번에 구독을 해지
        compositeDisposable.dispose();
    }
}
