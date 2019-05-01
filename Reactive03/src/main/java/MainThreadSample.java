import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 메인 스레드에서 처리 작업을 하는 예
 */
public class MainThreadSample {
    public static void main(String[] args) {
        System.out.println("Start");

        Flowable.just(1, 2, 3)
                // 구독
                .subscribe(new ResourceSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer data) {
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
    }
}
