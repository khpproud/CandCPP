import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

/**
 * MissingBackpressureException 이 발생하는 예
 */
public class MissingBackpressureFlowableSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(10L, TimeUnit.MICROSECONDS)
                // 통지시 정보를 출력
                .doOnNext(value -> System.out.println("emit : " + value))
                .onBackpressureLatest();

        flowable
                // 각 스레드에서 데이터를 받음
                .observeOn(Schedulers.computation())
                // 구독
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 무제한으로 데이터를 통지
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long value) {
                        // 1000ms를 기다린 후 처리
                        try {
                            System.out.println("waiting...");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("received : " + value);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.err.println("Error : " + t);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Complete");
                    }
                });

        // 잠시 기다림
        Thread.sleep(5000L);
    }
}
