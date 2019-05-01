import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

import java.util.concurrent.TimeUnit;

/**
 * observeOn 메서드로 bufferSize 를 지정하는 예
 */
public class ObserveOnSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable =
                // 300ms 마다 0부터 시작하는 데이터를 통지하는 Flowable을 생성
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // BackpressureMode.DROP 을 설정했을 때와 마찬가지로 동작
                .onBackpressureDrop();

        flowable
                // 비동기로 데이터를 받게 하고, 버퍼 크기를 1로 설정
                .observeOn(Schedulers.computation(), false, 1)
                // 구독
                .subscribe(new ResourceSubscriber<Long>() {
                    @Override
                    public void onNext(Long data) {
                        // 무거운 처리 작업을 한다고 가정하고 1000ms를 기다림
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }

                        // 받은 데이터를 출력
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

        // 잠시 기다림
        Thread.sleep(7000L);
    }
}
