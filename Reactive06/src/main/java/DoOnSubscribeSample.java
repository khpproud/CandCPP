import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * doOnSubscribe(onSubscribe) 예
 */
public class DoOnSubscribeSample {
    public static void main(String[] args) {
        Flowable.range(1, 5)
                // 구독 시작시 로그를 출력
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe"))
                // 구독
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("--- Subscriber: onSubscribe");
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer data) {
                        System.out.println("--- Subscribe: onNext : " + data);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
