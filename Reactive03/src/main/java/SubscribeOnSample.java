import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * subscribeOn 스케줄러 예
 */
public class SubscribeOnSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.computation())          // RxComputationThreadPool
                .subscribeOn(Schedulers.io())                   // RxCachedThreadScheduler
                .subscribeOn(Schedulers.single())               // RxSingleScheduler
                .subscribe(data ->
                        System.out.println(Thread.currentThread().getName() + " : " + data));

        // 잠시 기다림
        Thread.sleep(1000L);
    }
}
