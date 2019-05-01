import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;

import java.util.concurrent.TimeUnit;

/**
 * 메인 스레드가 아닌 스레드에서 처리 작업을 하는 Flowable 예
 */
public class NonMainThreadSample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start");

        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // 구독
                .subscribe(new ResourceSubscriber<Long>() {
                    @Override
                    public void onNext(Long data) {
                        System.out.println(Thread.currentThread().getName() + " : " + data);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(Thread.currentThread().getName() + " : Complete");
                    }
                });

        System.out.println("End");

        // 잠시 기다림
        Thread.sleep(1000L);
    }
}
