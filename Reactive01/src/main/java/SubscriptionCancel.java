import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

// 구독을 중도 해지하는 예
public class SubscriptionCancel {
    public static void main(String[] args) throws InterruptedException {
        // 200ms 마다 값을 통지하는 Flowable
        Flowable.interval(200L, TimeUnit.MILLISECONDS)
                .subscribe(new Subscriber<Long>() {
                    private Subscription subscription;
                    private long startTime;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.startTime = System.currentTimeMillis();
                        this.subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        // 구독 시작부터 500ms가 지나면 구독을 해지하고 처리를 중지
                        if ((System.currentTimeMillis() - startTime) > 500) {
                            subscription.cancel();          // 구독을 해지
                            System.out.println("Subscription canceled!");
                            return;
                        }

                        System.out.println("data = " + aLong);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Completed!");
                    }
                });
        // 잠시 기다림
        Thread.sleep(2000);
    }
}
