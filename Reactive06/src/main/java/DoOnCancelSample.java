import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

/**
 * doOnCancel(onCancel) 예
 */
public class DoOnCancelSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(100L, TimeUnit.MILLISECONDS)
                // 구독 해지시 로그를 출력
                .doOnCancel(() -> System.out.println("doOnCancel"))
                // 구독
                .subscribe(new Subscriber<Long>() {
                    private long startTime;
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.startTime = System.currentTimeMillis();
                        this.subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long data) {
                        // 구독 시작 시각으로부터 300ms 후 구독을 해지
                        if ((System.currentTimeMillis() - startTime) > 300L) {
                            System.out.println("구독 해지");
                            subscription.cancel();
                            return;
                        }
                        System.out.println(data);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Complete");
                    }
                });
        // 잠시 기다림
        Thread.sleep(1000L);
    }
}
