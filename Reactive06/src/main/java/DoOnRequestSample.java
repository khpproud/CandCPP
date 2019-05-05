import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * doOnRequest(size) 예
 */
public class DoOnRequestSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.range(1, 3)
                // 데이터 개수 요청할 시 로그를 출력
                .doOnRequest(size -> System.out.println("기존 데이터 : size = " + size))
                // Subscriber 처리를 다른 스레드에서 실행
                .observeOn(Schedulers.computation())
                // 데이터 개수 요청할 시 로그를 출력
                .doOnRequest(size -> System.out.println("--- observeOn 적용 후 : " + size))
                // 구독
                .subscribe(new Subscriber<Integer>() {
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(1);
                    }

                    @Override
                    public void onNext(Integer data) {
                        System.out.println(data);
                        subscription.request(1);
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
